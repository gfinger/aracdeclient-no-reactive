package org.makkiato.arcadeclient.request;

import java.util.Map;
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
        var payload = new CommandPayload("create database :dbname", Map.of("dbname", dbname));
        var status = client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(StatusResponseBody.class);
        return status.result().equals("ok");
    }

}
