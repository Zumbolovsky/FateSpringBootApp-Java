package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.redis.Test
import br.com.zumbolovsky.fateapp.domain.redis.TestRepository
import org.springframework.stereotype.Service

@Service
class RedisService(
    private var testRepository: TestRepository) {

    fun save(test: Test): Test = testRepository.save(test)

    fun findById(id: Int): Test =
        testRepository.findById(id)
            .orElseThrow { RuntimeException("Test does not exist!") }

    fun findAll(): MutableIterable<Test> = testRepository.findAll()

    fun deleteById(id: Int) = testRepository.deleteById(id)

}
