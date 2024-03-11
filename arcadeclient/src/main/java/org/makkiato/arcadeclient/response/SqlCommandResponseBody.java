package org.makkiato.arcadeclient.response;

import java.util.Map;

public record SqlCommandResponseBody(String user, String version, String serverName, Map<String, Object>[] result) {

}
