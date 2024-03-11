package org.makkiato.arcadeclient.response;

import java.io.IOException;

import org.makkiato.arcadeclient.exception.server.ClientError;
import org.makkiato.arcadeclient.exception.server.ServerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@Component
public class ArcadedbResponseErrorHandler implements ResponseErrorHandler {
    ObjectMapper mapper;

    public ArcadedbResponseErrorHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().equals(HttpStatus.OK);
    }

    @Override
    public void handleError(@NonNull ClientHttpResponse response) throws IOException {
        try {
            ErrorResponseBody error = mapper.readValue(response.getBody(), ErrorResponseBody.class);
            var statusCode = response.getStatusCode();
            if (statusCode.is4xxClientError()) {
                throw new ClientError(error == null ? new ErrorResponseBody() : error, response.getStatusCode());
            } else {
                throw new ServerError(error == null ? new ErrorResponseBody() : error, response.getStatusCode());
            }
        } catch (MismatchedInputException ex) {
            throw new ClientError(new ErrorResponseBody(), response.getStatusCode());
        }
    }

}
