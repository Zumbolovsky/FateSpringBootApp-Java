package br.com.zumbolovsky.fateapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory

@Configuration
class CachingConfig {

    @Bean
    fun jedisConnectionFactory(@Value("\${spring.redis.host}") host: String,
                               @Value("\${spring.redis.port}") port: Int): JedisConnectionFactory =
        JedisConnectionFactory(RedisStandaloneConfiguration(host, port))
}