package org.makkiato.arcadeclient.configuration;

import java.util.Base64;

import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.makkiato.arcadeclient")
@ConfigurationPropertiesScan
public class ApplicationConfiguration {
        @Bean
        RestClient restClient(ConnectionConfiguration connectionConfiguration, ResponseErrorHandler errorHandler) {
                RestClient client = RestClient.builder()
                                .baseUrl(UriComponentsBuilder.newInstance()
                                                .scheme("http")
                                                .host(connectionConfiguration.host())
                                                .port(connectionConfiguration.port())
                                                .path("/api/v1")
                                                .toUriString())
                                .defaultHeaders(headers -> {
                                        headers.add(HttpHeaders.AUTHORIZATION, encodeBasic(
                                                        connectionConfiguration.username(),
                                                        connectionConfiguration.password()));
                                        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
                                        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
                                })
                                .defaultStatusHandler(errorHandler)
                                .build();

                return client;
        }

        private String encodeBasic(String username, String password) {
                return "Basic " + Base64
                                .getEncoder()
                                .encodeToString((username + ":" + password).getBytes());
        }
}
