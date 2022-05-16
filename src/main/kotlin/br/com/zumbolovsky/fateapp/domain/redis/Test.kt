package br.com.zumbolovsky.fateapp.domain.redis

import org.springframework.data.redis.core.RedisHash

@RedisHash("Test")
data class Test(
    val id: Int,
    val name: String)
