package br.com.zumbolovsky.fateapp.domain.redis

import org.springframework.data.keyvalue.repository.KeyValueRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: KeyValueRepository<Test, Int>