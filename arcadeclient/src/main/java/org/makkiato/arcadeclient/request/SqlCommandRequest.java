package org.makkiato.arcadeclient.request;

import java.util.function.BiFunction;

import org.springframework.web.client.RestClient;

public class SqlCommandRequest {
    RestClient client;

    public SqlCommandRequest(RestClient client) {
        this.client = client;
    }

    public static SqlCommandRequest withClient(RestClient client) {
        return new SqlCommandRequest(client);
    }

    public <T> T apply(CommandPayload payload, Class<T> clazz) {
        return client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(clazz);
    }

}
