package br.com.zumbolovsky.fateapp.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.math.BigInteger

data class MainCharacter(
    val name: String,
    val level: Short,
    val saintQuartz: Long,
    val qp: BigInteger) {
    @BsonId private val key: Id<MainCharacter> = newId()
}
