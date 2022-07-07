package br.com.zumbolovsky.fateapp

import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLSetup {
    private val image = "postgres:latest"
    private val port = 5432

    private val container =
        PostgreSQLContainer(image)
            .withExposedPorts(port)

    private val jdbcUrl: String
    get() = container.jdbcUrl

    private val username: String
    get() = container.username

    private val password: String
    get() = container.password

    init {
        container.start()
        System.setProperty("spring.datasource.url", jdbcUrl)
        System.setProperty("spring.datasource.username", username)
        System.setProperty("spring.datasource.password", password)
    }
}
