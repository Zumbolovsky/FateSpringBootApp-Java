package br.com.zumbolovsky.fateapp

import org.testcontainers.containers.MongoDBContainer

class MongoDBSetup {
    private val image = "mongo:latest"
    private val port = 27017

    private val container =
        MongoDBContainer(image)
            .withExposedPorts(port)

    private val uri: String
    get() = container.replicaSetUrl

    private val database: String
    get() = "test"

    init {
        container.start()
        System.setProperty("spring.data.mongodb.uri", uri)
        System.setProperty("spring.data.mongodb.database", database)
    }
}
