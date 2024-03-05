package org.makkiato.arcadeclient.request.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.configuration.ApplicationConfiguration;
import org.makkiato.arcadeclient.exception.server.ArcadedbServerException;
import org.makkiato.arcadeclient.exception.server.IllegalArgumentException;
import org.makkiato.arcadeclient.request.DatabaseCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

@SpringJUnitConfig(ApplicationConfiguration.class)
class DatabaseCreateRequestTest {
    @Autowired
    RestClient client;

    @Test
    void apply() {
        var request = new DatabaseCreateRequest(client);
        try {
        var response = request.apply("dbtest");
        assertTrue(response);
        } catch(ArcadedbServerException ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals("Cannot execute command", ex.getBody().error());
            assertEquals("Database 'dbtest' already exists", ex.getBody().detail());
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        }
    }
}
