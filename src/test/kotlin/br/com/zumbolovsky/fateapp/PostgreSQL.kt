package br.com.zumbolovsky.fateapp

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import java.util.Properties

object PostgreSQL {

    @ExtendWith(Extension::class)
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Container

    class Extension: BeforeAllCallback, AfterAllCallback {

        private val postgreSQLContainer = PostgreSQLContainer("postgres:latest")

        override fun beforeAll(p0: ExtensionContext?) {
            postgreSQLContainer.start()
            System.setProperty("spring.datasource.url", postgreSQLContainer.jdbcUrl)
            System.setProperty("spring.datasource.username", postgreSQLContainer.username)
            System.setProperty("spring.datasource.password", postgreSQLContainer.password)
        }

        override fun afterAll(p0: ExtensionContext?) {
            postgreSQLContainer.stop()
        }
    }
}