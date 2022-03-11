package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.config.security.JwtToken
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
class LoginController(private var authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: AuthRequest): ResponseEntity<Nothing> =
        try {
            val authenticate: Authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(request.user, request.password))
            val user = UserInfo(
                user = authenticate.name,
                password = authenticate.credentials as String)
            ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    JwtToken.generateAccessToken(user))
                .build()
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
}