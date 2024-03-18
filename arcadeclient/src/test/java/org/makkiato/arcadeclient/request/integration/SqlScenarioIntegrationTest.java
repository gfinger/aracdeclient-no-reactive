package org.makkiato.arcadeclient.request.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.makkiato.arcadeclient.base.ArcadeClient;
import org.makkiato.arcadeclient.exception.server.ClientError;
import org.makkiato.arcadeclient.exception.server.ServerError;
import org.makkiato.arcadeclient.request.ArcadeTemplate;
import org.makkiato.arcadeclient.request.CommandPayload;
import org.makkiato.arcadeclient.request.DatabaseManagementRequests;
import org.makkiato.arcadeclient.request.ServerInfoRequest;
import org.makkiato.arcadeclient.request.SqlCommandRequest;
import org.makkiato.arcadeclient.request.model.Description;
import org.makkiato.arcadeclient.request.model.Event;
import org.makkiato.arcadeclient.request.model.Person;
import org.makkiato.arcadeclient.response.ServerInfoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import static org.makkiato.arcadeclient.query.Condition.And;
import static org.makkiato.arcadeclient.query.Condition.Equals;
import static org.makkiato.arcadeclient.query.Select.SelectFrom;

@SpringJUnitConfig(ApplicationConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class SqlScenarioIntegrationTest {
    @Autowired
    ArcadeClient arcadeClient;
    @Autowired
    ArcadeTemplate arcadeTemplate;

    @Autowired
    RestClient client;
    String dbname;

    @BeforeEach
    void initEach() {
        client = arcadeClient.getRestClient();
        dbname = arcadeClient.getDbName();
    }

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
        var request = new DatabaseManagementRequests(client);
        try {
            var response = request.createDatabase(dbname);
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
    void executeAlterDatabaseScript() {
        var script = new String[] {
                "alter database `arcadedb.dateImplementation` `java.time.LocalDate`",
                "alter database `arcadedb.dateTimeImplementation` `java.time.LocalDateTime`",
                "alter database dateformat \"dd MM yyyy GG\"",
                "alter database datetimeformat \"dd MM yyyy GG HH:mm:ss\""
        };
        try {
            var response = SqlCommandRequest.withClient(client).apply(
                    new CommandPayload("sqlscript", script, Collections.EMPTY_MAP), dbname);
            assertThat(response).isNotNull();
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(ServerError.class);
            var serverException = (ServerError) ex;
            assertThat(serverException.getBody().exceptionArgs()).isNotBlank();
        }
    }

    @Test
    @Order(4)
    void createDocumentIfNotExists() {
        var response = SqlCommandRequest.withClient(client).apply(
                new CommandPayload("create document type Description if not exists"),
                dbname);
        assertThat(response).isNotNull();
        assertThat(response.user()).isNotBlank();
        assertThat(response.version()).isNotBlank();
    }

    @Test
    @Order(5)
    void executeSqlscript() {
        var script = new String[] {
                "create vertex type Person if not exists",
                "create property Person.name if not exists String (mandatory true, notnull true)",
                "create index if not exists on Person (name) unique",
                "create property Person.dateOfBirth if not exists Long",
                "create property Person.dateOfDeath if not exists Long",
                "create vertex type Event if not exists",
                "create property Event.title if not exists String (mandatory true, notnull true)",
                "create index if not exists on Event (title) notunique",
                "create property Event.date if not exists Long",
                "create property Event.description if not exists Embedded",
                "create edge type IsInvolved if not exists",
                "create property IsInvolved.`@out` if not exists link of Person",
                "create property IsInvolved.`@in` if not exists link of Event"
        };
        try {
            var response = SqlCommandRequest.withClient(client).apply(
                    new CommandPayload("sqlscript", script, Map.of("name", "Friedrich II.")),
                    dbname);
            assertThat(response).isNotNull();
            assertThat(response.user()).isNotBlank();
            assertThat(response.version()).isNotBlank();
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(ServerError.class);
            var serverException = (ServerError) ex;
            assertThat(serverException.getBody().error()).isEqualTo("Found duplicate key in index");
            assertThat(serverException.getBody().detail()).matches("Duplicated key .* found on index .*");
            assertThat(serverException.getBody().exception())
                    .isEqualTo("com.arcadedb.exception.DuplicatedKeyException");
            assertThat(serverException.getBody().exceptionArgs()).isNotBlank();
        }
    }

    @Test
    @Order(6)
    void insertVertex() throws JsonMappingException, JsonProcessingException {
        var dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy GG");
        var dateOfDeath = LocalDate.parse("01 01 0001 AD", dateFormatter);
        var dateOfBirth = LocalDate.parse("01 01 0001 BC", dateFormatter);
        var person = arcadeTemplate.insertDocument(
                new Person(null, null, null, "Chlodwig", dateOfBirth, dateOfDeath));
        assertThat(person).isNotNull();
        assertThat(person.getRid()).isNotBlank();
        assertThat(person.getCat()).isNotBlank();
        assertThat(person.getType()).isEqualTo("Person");
        assertThat(person.getName()).isEqualTo("Chlodwig");
        assertThat(person.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(person.getDateOfDeath()).isEqualTo(dateOfDeath);
    }

    @Test
    @Order(7)
    void insertVertexWithEmbeddedDocument() throws JsonMappingException, JsonProcessingException {
        var dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy GG");
        var eventDate = LocalDate.parse("01 01 0001 AD", dateFormatter);
        var description = new Description(null, null, "Was this the day when Jesus was born?");
        var event = arcadeTemplate
                .insertDocument(new Event("Event", null, null, eventDate, "Birth of Jesus", description));
        assertThat(event).isNotNull();
        assertThat(event.getRid()).isNotBlank();
        assertThat(event.getCat()).isNotBlank();
        assertThat(event.getType()).isEqualTo("Event");
        assertThat(event.getTitle()).isEqualTo("Birth of Jesus");
        assertThat(event.getDate()).isEqualTo(eventDate);
        assertThat(event.getDescription()).usingRecursiveComparison().ignoringFields("cat", "type").isEqualTo(description);
        assertThat(event.getDescription().getCat()).isEqualTo("d");
        assertThat(event.getDescription().getType()).isEqualTo("Description");
    }

    @Test
    @Order(8)
    void select() throws JsonProcessingException, IllegalArgumentException {
        var dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy GG");
        var dateOfDeath = LocalDate.parse("01 01 0001 AD", dateFormatter);  
        var dateOfBirth = LocalDate.parse("01 01 0001 BC", dateFormatter);  
        var selected = arcadeTemplate.selectDocument(SelectFrom("Person").Where(Equals("dateOfBirth", dateOfBirth)), Person.class);
        assertThat(selected).isNotEmpty();
        assertThat(selected.get(0).getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(selected.get(0).getDateOfDeath()).isEqualTo(dateOfDeath);
    }

    @Test
    @Order(9)
    void createEdge() {

    }

    @Test
    @Order(99)
    void dropDatabase() {
        var request = new DatabaseManagementRequests(client);
        try {
            var response = request.dropDatabase(dbname);
            assertThat(response).isTrue();
        } catch (Exception ex) {
            assertThat(ex)
                    .isInstanceOf(ServerError.class);
            var serverException = (ServerError) ex;
            assertThat(serverException.getBody().error()).isEqualTo("Internal error");
            assertThat(serverException.getBody().detail()).matches("Database .* does not exist");
            assertThat(serverException.getStatus()).isIn(HttpStatus.INTERNAL_SERVER_ERROR);
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
