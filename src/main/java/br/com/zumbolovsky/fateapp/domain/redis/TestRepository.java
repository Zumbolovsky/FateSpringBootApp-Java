package br.com.zumbolovsky.fateapp.domain.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends KeyValueRepository<Test, Integer> {
}