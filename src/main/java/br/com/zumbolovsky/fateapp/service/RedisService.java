package br.com.zumbolovsky.fateapp.service;

import br.com.zumbolovsky.fateapp.domain.redis.Test;
import br.com.zumbolovsky.fateapp.domain.redis.TestRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"test"})
public class RedisService {

    private final TestRepository testRepository;

    public RedisService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @CachePut(key = "#test.id")
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Cacheable
    public Test findById(Integer id) {
        return testRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {
        testRepository.deleteById(id);
    }

}
