package org.makkiato.arcadeclient.request.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "org.makkiato.arcadedb")
public record ConnectionConfiguration(String host, Integer port, String username, String password, String database) {

    public static final int DEFAULT_PORT = 2480;
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "playwithdata";
    public static final String DEFAULT_DATABASE = "testdb";

    @ConstructorBinding
    public ConnectionConfiguration(String host, Integer port, String username, String password, String database) {
        this.host = host != null ? host : DEFAULT_HOST;
        this.port = port != null ? port : DEFAULT_PORT;
        this.username = username != null ? username : DEFAULT_USERNAME;
        this.password = password != null ? password : DEFAULT_PASSWORD;
        this.database = database != null ? database : DEFAULT_DATABASE;
    }
}
