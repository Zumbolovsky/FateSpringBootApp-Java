package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.EXPIRATION_TIME
import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.KEY
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import kotlin.Throws
import java.security.Key
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse,
                                          chain: FilterChain,
                                          auth: Authentication) {
        val exp = Date(System.currentTimeMillis() + EXPIRATION_TIME)
        val key: Key = Keys.hmacShaKeyFor(KEY.toByteArray())
        val claims: Claims =
            Jwts.claims().setSubject((auth.principal as User).username)
        val token: String =
            Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp).compact()
        res.addHeader("token", token)
    }
}