package br.com.zumbolovsky.fateapp;

import org.testcontainers.containers.MongoDBContainer;

public class MongoDBSetup {
    private static final String image = "mongo:latest";
    private static final Integer port = 27017;

    private static MongoDBContainer container() {
        final MongoDBContainer mongoDBContainer = new MongoDBContainer(image);
        mongoDBContainer.withExposedPorts(port);
        return mongoDBContainer;
    }

    private String database() {
        return "test";
    }

    public MongoDBSetup() {
        final MongoDBContainer container = container();
        container.start();
        System.setProperty("spring.data.mongodb.uri", container.getReplicaSetUrl());
        System.setProperty("spring.data.mongodb.database", database());
    }
}
