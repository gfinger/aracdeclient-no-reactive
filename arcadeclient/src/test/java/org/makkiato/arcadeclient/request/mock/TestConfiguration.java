package org.makkiato.arcadeclient.request.mock;

import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.makkiato.arcadeclient.response.ArcadedbResponseErrorHandler;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestConfiguration {
    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    ArcadedbResponseErrorHandler arcadedbResponseErrorHandler(ObjectMapper objectMapper) {
        return new ArcadedbResponseErrorHandler(objectMapper);
    }

    @Bean
    RestClientCustomizer restClientCustomizer(ArcadedbResponseErrorHandler arcadedbResponseErrorHandler) {
        return (restClientBuilder) -> restClientBuilder
                .defaultStatusHandler(arcadedbResponseErrorHandler);
    }

    @Bean
    ServerInfoRequest serverInfoRequest(RestClient client) {
        return new ServerInfoRequest(client);
    }
}
