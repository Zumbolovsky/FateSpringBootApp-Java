package br.com.zumbolovsky.fateapp

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.PropertiesPropertySource
import org.testcontainers.containers.MongoDBContainer
import java.util.Properties

object MongoDB {

    @ExtendWith(Extension::class)
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Container

    private class Extension: BeforeAllCallback, AfterAllCallback {

        private val mongoDBContainer = MongoDBContainer("mongo:latest")

        override fun beforeAll(p0: ExtensionContext?) {
            mongoDBContainer.start()
            System.setProperty("spring.data.mongodb.uri", mongoDBContainer.replicaSetUrl)
            System.setProperty("spring.data.mongodb.database", "test")
        }

        override fun afterAll(p0: ExtensionContext?) {
            mongoDBContainer.stop()
        }
    }
}