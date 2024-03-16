package org.makkiato.arcadeclient.request;

import org.makkiato.arcadeclient.response.StatusResponseBody;
import org.springframework.web.client.RestClient;

public class DatabaseManagementRequests {
    RestClient client;

    public DatabaseManagementRequests(RestClient client) {
        this.client = client;
    }

    public Boolean createDatabase(String dbname) {
        var payload = new CommandPayload(String.format("create database %s", dbname));
        var status = client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(StatusResponseBody.class);
        return status != null && status.result() != null && status.result().equals("ok");
    }

    public Boolean dropDatabase(String dbname) {
        var payload = new CommandPayload(String.format("drop database %s", dbname));
        var status = client.post()
                .uri("/server")
                .body(payload)
                .retrieve()
                .body(StatusResponseBody.class);
        return status != null && status.result() != null && status.result().equals("ok");
    }

}
