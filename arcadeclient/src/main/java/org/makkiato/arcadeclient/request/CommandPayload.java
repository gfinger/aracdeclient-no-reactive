package org.makkiato.arcadeclient.request;

import java.util.Map;

public record CommandPayload(String language, String command, Map<String, Object> params, String serializer) {
}
