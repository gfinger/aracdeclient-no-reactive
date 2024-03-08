package org.makkiato.arcadeclient.request.integration;

import org.junit.jupiter.api.Test;
import org.makkiato.arcadeclient.request.CommandPayload;
import org.makkiato.arcadeclient.request.SqlCommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

@SpringJUnitConfig(ApplicationConfiguration.class)
public class SqlCommandRequestTest {
    @Autowired
    RestClient client;

    @Test
    void apply() {
        SqlCommandRequest.withClient(client).apply(new CommandPayload("create document type Description if not exists"), null);
    }
}
