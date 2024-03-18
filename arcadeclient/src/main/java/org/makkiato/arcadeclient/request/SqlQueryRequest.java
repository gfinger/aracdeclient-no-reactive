package org.makkiato.arcadeclient.request;

import org.makkiato.arcadeclient.response.SqlCommandResponseBody;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestClient;

public class SqlQueryRequest {
    RestClient client;

    public SqlQueryRequest(RestClient client) {
        this.client = client;
    }

    public static SqlCommandRequest withClient(RestClient client) {
        return new SqlCommandRequest(client);
    }

    public SqlCommandResponseBody apply(@NonNull CommandPayload payload, @NonNull String dbname) {
        return client.post()
                .uri(String.format("/query/%s", dbname))
                .body(payload)
                .retrieve()
                .body(SqlCommandResponseBody.class);
    }

}
