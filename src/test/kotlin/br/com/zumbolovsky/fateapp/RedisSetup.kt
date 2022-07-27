package br.com.zumbolovsky.fateapp

import org.testcontainers.containers.GenericContainer

class RedisSetup {
    private val image = "bitnami/redis:latest"
    private val port = 6379

    private val container =
        GenericContainer(image)
            .withExposedPorts(port)

    private val host: String
    get() = container.host


    init {
        container.addEnv("ALLOW_EMPTY_PASSWORD", "yes")
        container.start()
        System.setProperty("spring.redis.host", host)
        System.setProperty("spring.redis.port", port.toString())
    }
}
