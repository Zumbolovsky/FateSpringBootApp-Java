package br.com.zumbolovsky.fateapp.domain

import java.math.BigInteger

data class MainCharacter(
    val name: String,
    val level: Short,
    val saintQuartz: Long,
    val qp: BigInteger
)
