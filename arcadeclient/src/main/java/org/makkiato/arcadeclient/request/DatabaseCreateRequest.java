package org.makkiato.arcadeclient.request;

import java.util.function.Function;

import org.makkiato.arcadeclient.response.StatusResponseBody;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class DatabaseCreateRequest implements Function<String, Boolean> {
    RestClient client;

    public DatabaseCreateRequest(RestClient client) {
        this.client = client;
    }

    @Override
    public Boolean apply(String dbname) {
        var payload = new CommandPayload("sql", "create database " + dbname, null, "json");
        var status = client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(StatusResponseBody.class);
        return status.result().equals("ok");
    }

}
