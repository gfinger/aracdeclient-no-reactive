package org.makkiato.arcadeclient.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public record CommandPayload(String language, String command, Map<String, Object> params, String serializer) {
    public CommandPayload(String command) {
        this("sql", command, Collections.emptyMap(), "record");
    }

    public CommandPayload(String command, Map<String, Object> params) {
        this("sql", command, params, "record");
    }

    public CommandPayload(String language, String[] commands, Map<String, Object> params) {
        this(language, Arrays.stream(commands).map(c -> String.format("%s", c)).collect(Collectors.joining(";")),
                params, "record");
    }
}
