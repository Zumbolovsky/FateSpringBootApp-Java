package br.com.zumbolovsky.fateapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import java.time.Duration;

import static java.lang.Thread.currentThread;

@Configuration
public class CachingConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Value("${spring.redis.host}") String host,
                                                         @Value("${spring.redis.port}") Integer port) {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return it -> {
            it.withCacheConfiguration(
                    "user",
                    RedisCacheConfiguration
                            .defaultCacheConfig(currentThread().getContextClassLoader())
                            .disableCachingNullValues()
                            .entryTtl(Duration.ofMinutes(2)));
            it.withCacheConfiguration(
                    "test",
                    RedisCacheConfiguration
                            .defaultCacheConfig(currentThread().getContextClassLoader())
                            .disableCachingNullValues()
                            .entryTtl(Duration.ofMinutes(2)));
        };
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
}