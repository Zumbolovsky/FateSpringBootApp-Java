package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.HEADER_NAME
import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.KEY
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authManager: AuthenticationManager) :
    BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  chain: FilterChain) {
        val header = request.getHeader(HEADER_NAME)
        if (header == null) {
            chain.doFilter(request, response)
            return
        }
        val authentication = authenticate(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun authenticate(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_NAME)
        return if (token != null) {
            val user = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
            if (user != null)
                UsernamePasswordAuthenticationToken(user, null, ArrayList())
            else null
        } else null
    }
}