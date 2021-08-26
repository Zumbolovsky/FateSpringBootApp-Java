package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.Personagens
import br.com.zumbolovsky.fateapp.service.PersonagensService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonagensController(private val personagensService: PersonagensService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ["/personagens"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun save(@RequestBody personagens: PersonagensVO) {
        val request = Personagens()
        request.nome = personagens.nome
        personagensService.insert(request)
    }
}