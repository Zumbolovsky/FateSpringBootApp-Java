package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.mongo.MainCharacter
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.exists
import org.litote.kmongo.getCollection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MongoService {

    @Autowired private lateinit var mongoDB: MongoDatabase

    private final val logger: Logger = LoggerFactory.getLogger(MongoService::class.java)

    fun createMC(): MainCharacter {
        val mcCollection = mongoDB.getCollection<MainCharacter>()
        val mainCharacter = MainCharacter("test", random().toInt().toShort(), random().toLong(), random().toBigDecimal().toBigInteger())
        if (mcCollection.countDocuments() == 1L) {
            logger.info("Replacing existing main character...")
            mcCollection.deleteMany(MainCharacter::name exists true)
            mcCollection.insertOne(mainCharacter)
        } else {
            logger.info("Creating main character...")
            mcCollection.insertOne(mainCharacter)
        }
        return mainCharacter
    }

    fun random(): Double = Math.random().times(10000)

}
