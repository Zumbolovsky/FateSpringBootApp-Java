package br.com.zumbolovsky.fateapp.domain.redis

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Test")
data class Test(
    val id: Int,
    val name: String) : Serializable {
    companion object {
        private const val serialVersionUID: Long = -2254515702608287255L
    }
}
