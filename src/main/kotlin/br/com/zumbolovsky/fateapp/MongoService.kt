package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.domain.MainCharacter
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.exists
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class MongoService {

    @Autowired private lateinit var mongoDB: MongoDatabase

    fun createMC(): MainCharacter {
        val mcCollection = mongoDB.getCollection<MainCharacter>()
        val mainCharacter = MainCharacter("test", 1, 10, BigInteger("100000"))
        if (mcCollection.countDocuments() == 1L) {
            mcCollection.findOneAndReplace(
                MainCharacter::name exists true,
                mainCharacter
            )
        } else {
            mcCollection.insertOne(mainCharacter)
        }
        return mainCharacter
    }

}
