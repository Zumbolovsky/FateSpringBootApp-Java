package br.com.zumbolovsky.fateapp

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

internal class ContainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {

        // Initialize and start test containers
        PostgreSQLSetup()
        MongoDBSetup()
    }
}