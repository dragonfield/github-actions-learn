package com.example.integration;

import lombok.Getter;

@Getter
public class IntegrationTestConfig {

    private String restAssuredHost;

    private Integer restAssuredPort;

    private String databaseUrl;

    private String databaseUser;

    private String databasePassword;

    public IntegrationTestConfig() {
        reload();
    }

    void reload() {
        restAssuredHost = System.getProperty("rest.assured.host", "localhost");
        restAssuredPort = Integer.getInteger("rest.assured.port", 8080);
        databaseUrl = System.getProperty("database.url", "jdbc:postgresql://localhost:5432/mydb");
        databaseUser = System.getProperty("database.user", "appuser");
        databasePassword = System.getProperty("database.password", "password123");
    }

}

