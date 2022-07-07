package br.com.zumbolovsky.fateapp.domain.mongo

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.math.BigInteger

data class MainCharacter(
    @BsonId val key: Id<MainCharacter> = newId(),
    val name: String,
    val level: Short,
    val saintQuartz: Long,
    val qp: BigInteger)
