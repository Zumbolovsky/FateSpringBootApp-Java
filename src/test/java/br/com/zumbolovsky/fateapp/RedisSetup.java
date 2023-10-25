package br.com.zumbolovsky.fateapp;

import org.testcontainers.containers.GenericContainer;

public class RedisSetup {
    private static final String image = "bitnami/redis:latest";
    private static final Integer port = 6379;

    private static GenericContainer<?> container() {
        return new GenericContainer<>(image).withExposedPorts(port);
    }

    public RedisSetup() {
        final GenericContainer<?> container = container();
        container.addEnv("ALLOW_EMPTY_PASSWORD", "yes");
        container.start();
        System.setProperty("spring.redis.host", container.getHost());
        System.setProperty("spring.redis.port", port.toString());
    }
}
