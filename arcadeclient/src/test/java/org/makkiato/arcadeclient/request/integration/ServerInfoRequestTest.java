package org.makkiato.arcadeclient.request.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.configuration.ApplicationConfiguration;
import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

@SpringJUnitConfig(ApplicationConfiguration.class)
class ServerInfoRequestTest {
    @Autowired
    RestClient client;

    @Test
    void get() {
        var request = new ServerInfoRequest(client);
        var responseBody = request.get();
        assertNotNull(responseBody);
        assertNotNull(responseBody.user());
        assertNotNull(responseBody.version());
        assertNotNull(responseBody.serverName());
    }
}
