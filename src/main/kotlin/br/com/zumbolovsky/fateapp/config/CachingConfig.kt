package br.com.zumbolovsky.fateapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import java.time.Duration

@Configuration
class CachingConfig {

    @Bean
    fun jedisConnectionFactory(@Value("\${spring.redis.host}") host: String,
                               @Value("\${spring.redis.port}") port: Int): JedisConnectionFactory =
        JedisConnectionFactory(RedisStandaloneConfiguration(host, port))

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer =
        RedisCacheManagerBuilderCustomizer {
            it.withCacheConfiguration(
                    "user",
                    RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        .entryTtl(Duration.ofMinutes(2)))
        }
}