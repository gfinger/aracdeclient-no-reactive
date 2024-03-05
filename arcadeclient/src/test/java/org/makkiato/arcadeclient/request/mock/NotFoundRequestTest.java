package org.makkiato.arcadeclient.request.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.exception.server.ArcadedbServerException;
import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.makkiato.arcadeclient.response.ServerInfoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest
@ContextConfiguration(classes = { TestConfiguration.class })
public class NotFoundRequestTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    RestClient client;
    @Autowired
    ObjectMapper mapper;

    @Test
    void getNotFound() throws JsonProcessingException {
        server.expect(requestTo("/blafasel"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.TEXT_HTML)
                        .body(""));
        try {
            client.get()
                .uri("/blafasel")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ServerInfoResponseBody.class);
        } catch (ArcadedbServerException ex) {
            assertEquals(org.makkiato.arcadeclient.exception.server.NotFoundException.class, ex.getClass());
        }
    }
}
