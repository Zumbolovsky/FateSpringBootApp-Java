package br.com.zumbolovsky.fateapp.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServantRepository : JpaRepository<Servant?, Int?>