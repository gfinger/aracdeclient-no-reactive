package org.makkiato.arcadeclient.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body,
            @NonNull ClientHttpRequestExecution execution) {
        log(request, new String(body, StandardCharsets.UTF_8));
        try {
            var response = execution.execute(request, body);
            if (log.isDebugEnabled()) {
                var cachedResponse = new CachingHttpResponse(response);
                log(cachedResponse);
                response = cachedResponse;
            }
            return response;
        } catch (IOException ex) {
            log.error("Exception thrown by HttpRequest:", ex);
            throw new RuntimeException(ex);
        }
    }

    private void log(ClientHttpResponse response) {
        var builder = new StringBuilder();
        try {
            builder.append(String.format("Status Code: %s\n", response.getStatusCode().toString()));
        } catch (IOException ex) {
            log.error("Exception thrown by HttpRequest:", ex);
            throw new RuntimeException(ex);
        }
        response.getHeaders().forEach((header, values) -> builder.append(String.format("%s: %s\n", header, values)));
        try (var body = response.getBody()) {
            if (body != null) {
                var bodyS = StreamUtils.copyToString(body, StandardCharsets.UTF_8);
                builder.append(bodyS);
            }
        } catch (IOException ex) {
            log.error("Exception thrown by HttpRequest:", ex);
            throw new RuntimeException(ex);
        }
        log.debug(builder.toString());
    }

    private void log(HttpRequest request, String body) {
        var builder = new StringBuilder();
        builder.append(String.format("%s %s\n", request.getMethod(), request.getURI()));
        request.getHeaders().forEach((header, values) -> builder.append(String.format("%s: %s\n", header, values)));
        if (body != null) {
            builder.append(body);
        }
        log.debug(builder.toString());
    }

}
