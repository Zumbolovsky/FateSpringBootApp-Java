package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.redis.Test
import br.com.zumbolovsky.fateapp.domain.redis.TestRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
@CacheConfig(cacheNames = ["test"])
class RedisService(
    private var testRepository: TestRepository) {

    @CachePut(key = "#test.id")
    fun save(test: Test): Test = testRepository.save(test)

    @Cacheable
    fun findById(id: Int): Test =
        testRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }

    fun findAll(): MutableIterable<Test> = testRepository.findAll()

    @CacheEvict(key = "#id")
    fun deleteById(id: Int) = testRepository.deleteById(id)

}
