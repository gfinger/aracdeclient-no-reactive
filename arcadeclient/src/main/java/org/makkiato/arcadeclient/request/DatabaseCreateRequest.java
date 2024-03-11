package org.makkiato.arcadeclient.request;

import java.util.function.Function;

import org.makkiato.arcadeclient.response.StatusResponseBody;
import org.springframework.web.client.RestClient;

public class DatabaseCreateRequest implements Function<String, Boolean> {
    RestClient client;

    public DatabaseCreateRequest(RestClient client) {
        this.client = client;
    }

    @Override
    public Boolean apply(String dbname) {
        var payload = new CommandPayload(String.format("create database %s", dbname));
        var status = client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(StatusResponseBody.class);
        return status != null && status.result() != null && status.result().equals("ok");
    }

}
