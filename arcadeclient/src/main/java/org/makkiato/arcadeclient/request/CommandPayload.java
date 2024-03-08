package org.makkiato.arcadeclient.request;

import java.util.Collections;
import java.util.Map;

public record CommandPayload(String language, String command, Map<String, Object> params, String serializer) {
    public CommandPayload(String command) {
        this("sql", command, Collections.emptyMap(), "record");
    }
    public CommandPayload(String command, Map<String, Object> params) {
        this("sql", command, params, "record");
    }
}
