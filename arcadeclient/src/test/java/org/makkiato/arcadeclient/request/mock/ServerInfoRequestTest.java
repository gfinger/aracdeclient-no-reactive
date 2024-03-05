package org.makkiato.arcadeclient.request.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.makkiato.arcadeclient.response.ServerInfoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest
@ContextConfiguration(classes = { TestConfiguration.class })
public class ServerInfoRequestTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    RestClient client;
    @Autowired
    ObjectMapper mapper;

    @Test
    void getOk() throws JsonProcessingException {
        var serverInfo = new ServerInfoResponseBody("root", "23.3.1", "ArcadeDB_0", null);
        server.expect(requestTo("/server?mode=default"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(serverInfo)));
        var request = new ServerInfoRequest(client);
        var responseBody = request.get();
        assertNotNull(responseBody);
        assertEquals(serverInfo.user(), responseBody.user());
        assertEquals(serverInfo.version(), responseBody.version());
        assertEquals(serverInfo.serverName(), responseBody.serverName());
    }
}
 