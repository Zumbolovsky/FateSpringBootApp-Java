package br.com.zumbolovsky.fateapp;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLSetup {
    private static final String image = "postgres:latest";
    private static final Integer port = 5432;

    private static PostgreSQLContainer<?> container() {
        final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(image);
        postgreSQLContainer.withExposedPorts(port);
        return postgreSQLContainer;
    }

    public PostgreSQLSetup() {
        final PostgreSQLContainer<?> container = container();
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }
}
