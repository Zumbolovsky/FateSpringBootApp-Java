package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.ObjectUtils.isEmpty
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val userService : UserService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) : Unit =
        request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.also { header -> attemptAuthenticationFromToken(header, request) }
            .run { filterChain.doFilter(request, response) }

    private fun attemptAuthenticationFromToken(
        header: String,
        request: HttpServletRequest,
    ) {
        if (!isEmpty(header) && header.startsWith("Bearer ")) {
            // Get jwt token and validate
            header.split(" ").lastOrNull()!!
                .also { token ->
                    if (JwtToken.validate(token)) {
                        // Get user identity and set it on the spring security context
                        SecurityContextHolder.getContext().authentication = getAuthentication(request, token)
                    }
                }
        }

    }
    private fun getAuthentication(
        request: HttpServletRequest,
        token: String
    ) : Authentication =
        mapToUsernamePasswordAuthenticationToken(
            userService.loadUserByUsername(JwtToken.getUsername(token)),
            request
        )

    private fun mapToUsernamePasswordAuthenticationToken(
        userDetails: UserDetails,
        request: HttpServletRequest
    ) : UsernamePasswordAuthenticationToken =
        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            .also { authenticationToken ->
                authenticationToken.details = WebAuthenticationDetailsSource()
                    .buildDetails(request)
            }
}