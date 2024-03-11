package org.makkiato.arcadeclient.response;

public record ServerInfoResponseBody(String user, String version, String serverName,
        HighAvailabilityInfo ha) {

}
