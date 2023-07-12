package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.config.error.APIResponse
import br.com.zumbolovsky.fateapp.domain.mongo.MainCharacter
import br.com.zumbolovsky.fateapp.service.MongoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpHeaders
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
class SecuredTestController(
    private val mongoService: MongoService) {

    @PostMapping("/secured/mongo/test")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @Operation(summary = "Testing mongo")
    fun insertMongo(): APIResponse<MainCharacter> =
        APIResponse(body = mongoService.createMC())

}
