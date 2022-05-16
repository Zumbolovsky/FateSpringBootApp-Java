package br.com.zumbolovsky.fateapp.domain.redis

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: CrudRepository<Test, Int>