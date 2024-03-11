package org.makkiato.arcadeclient.request.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.exception.server.ClientError;
import org.makkiato.arcadeclient.exception.server.ServerError;
import org.makkiato.arcadeclient.request.CommandPayload;
import org.makkiato.arcadeclient.request.DatabaseCreateRequest;
import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.makkiato.arcadeclient.request.SqlCommandRequest;
import org.makkiato.arcadeclient.response.ServerInfoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

@SpringJUnitConfig(ApplicationConfiguration.class)
public class SqlScenarioIntegrationTest {
    @Autowired
    RestClient client;

    @Test
    @Order(1)
    void getServerInfo() {
        var request = new ServerInfoRequest(client);
        var response = request.get();
        assertThat(response).isNotNull();
        assertThat(response.user()).isNotBlank();
        assertThat(response.version()).isNotBlank();
        assertThat(response.serverName()).isNotBlank();
    }

    @Test
    @Order(2)
    void createDatabaseIfNotExists() {
        var request = new DatabaseCreateRequest(client);
        try {
            var response = request.apply("dbtest");
            assertThat(response).isTrue();
        } catch (Exception ex) {
            assertThat(ex)
                    .isInstanceOf(ClientError.class);
            var serverException = (ClientError) ex;
            assertThat(serverException.getBody().error()).isEqualTo("Cannot execute command");
            assertThat(serverException.getBody().detail()).matches("Database .* already exists");
            assertThat(serverException.getStatus()).isIn(HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    @Order(3)
    void createDocumentIfNotExists() {
        var response = SqlCommandRequest.withClient(client).apply(
                new CommandPayload("create document type Description if not exists"),
                "dbtest");
        assertThat(response).isNotNull();
        assertThat(response.user()).isNotBlank();
        assertThat(response.version()).isNotBlank();
    }

    @Test
    @Order(3)
    void executeSqlscript() {
        var script = new String[] {
                "create vertex type Person if not exists",
                "create property Person.name if not exists String (mandatory true, notnull true)",
                "create index if not exists on Person (name) unique",
                "insert into Person set name = :name"
        };
        try {
            var response = SqlCommandRequest.withClient(client).apply(
                    new CommandPayload("sqlscript", script, Map.of("name", "Friedrich II.")),
                    "dbtest");
            assertThat(response).isNotNull();
            assertThat(response.user()).isNotNull();
            assertThat(response.version()).isNotNull();
        } catch (Exception ex) {
            assertThat(ex)
                    .isInstanceOf(ServerError.class);
            var serverException = (ServerError) ex;
            assertThat(serverException.getBody().error()).isEqualTo("Found duplicate key in index");
            assertThat(serverException.getBody().detail()).matches("Duplicated key .* found on index .*");
            assertThat(serverException.getBody().exception())
                    .isEqualTo("com.arcadedb.exception.DuplicatedKeyException");
            assertThat(serverException.getBody().exceptionArgs()).isNotBlank();
        }
    }

    @Test
    void getNotFound() {
        try {
            client.get()
                    .uri("/blafasel")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ServerInfoResponseBody.class);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(ClientError.class);
            var requestException = (ClientError) ex;
            assertThat(requestException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
