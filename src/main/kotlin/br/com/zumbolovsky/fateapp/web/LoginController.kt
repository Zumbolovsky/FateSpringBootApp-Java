package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.config.security.JwtToken
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(private val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun login(@RequestBody @Validated request: AuthRequest): ResponseEntity<Nothing> =
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.user, request.password))
            .let {
                ResponseEntity.ok()
                    .header(
                        HttpHeaders.AUTHORIZATION,
                        JwtToken.generateAccessToken(
                            UserInfo(
                                user = it.name,
                                password = it.credentials as String)))
                    .build()
            }
}