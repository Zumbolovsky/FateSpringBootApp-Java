package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.service.UserService
import ch.qos.logback.core.util.OptionHelper.isEmpty
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(private val userService : UserService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        request.getHeader(HttpHeaders.AUTHORIZATION)
            .also { header ->
                if (isEmpty(header) || !header.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response)
                    return
                }

                // Get jwt token and validate
                header.split(" ").toTypedArray()[1].trim { it <= ' ' }
                    .also { token ->
                        if (!JwtToken.validate(token)) {
                            filterChain.doFilter(request, response)
                            return
                        }

                        // Get user identity and set it on the spring security context
                        SecurityContextHolder.getContext().authentication =
                            userService.loadUserByUsername(JwtToken.getUsername(token))
                                .let { userDetails ->
                                    UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.authorities
                                    ).also { authenticationToken ->
                                        authenticationToken.details =
                                            WebAuthenticationDetailsSource().buildDetails(request)
                                    }
                                }
                    }
            }

        filterChain.doFilter(request, response)
    }
}