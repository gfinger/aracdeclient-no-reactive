package org.makkiato.arcadeclient.request;

import java.util.function.Supplier;

import org.makkiato.arcadeclient.response.ServerInfoResponseBody;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ServerInfoRequest implements Supplier<ServerInfoResponseBody> {
    RestClient client;

    public ServerInfoRequest(RestClient client) {
        this.client = client;
    }

    public ServerInfoResponseBody get() {
        return client.get()
                .uri("/server?mode=default")
                .retrieve()
                .body(ServerInfoResponseBody.class);
    }
}
