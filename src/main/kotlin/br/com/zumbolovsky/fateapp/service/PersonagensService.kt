package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.Personagens
import br.com.zumbolovsky.fateapp.domain.PersonagensRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonagensService(private val personagensRepository: PersonagensRepository) {

    @Transactional
    fun insert(personagens: Personagens) {
        personagensRepository.save(personagens)
    }
}