package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService,
    private val jwtTokenFilter: JwtTokenFilter) {

    companion object SecurityConstants {
        const val SECURED_URLS = "/secured/**"
        const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
        const val ISSUER = "fateapp"
        const val EXPIRATION_TIME = 1000 * 60 * 30
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors { corsConfigurer ->
                corsConfigurer.configurationSource(
                    UrlBasedCorsConfigurationSource().also { config ->
                        config.registerCorsConfiguration("/**",
                            CorsConfiguration().applyPermitDefaultValues())
                    })
            }
            .csrf { it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, exception ->
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        exception.message)
                }
            }
            .authorizeHttpRequests {
                it.antMatchers(SECURED_URLS).authenticated()
                    .anyRequest().permitAll()

            }
            .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()

    @Bean
    fun authenticationManager(): AuthenticationManager =
        AuthenticationManager { auth ->
            userService.loadByUsernameAndPassword(auth.name, auth.credentials as String)
                .let { auth }
        }
}