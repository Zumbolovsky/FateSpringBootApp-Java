package br.com.zumbolovsky.fateapp.config.database

import com.mongodb.ConnectionString
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig {

    @Bean
    fun mongoDB(@Value("\${spring.data.mongodb.uri}") connectionString: String,
                @Value("\${spring.data.mongodb.database}") database: String): MongoDatabase =
        KMongo.createClient(ConnectionString(connectionString))
            .getDatabase(database)
}