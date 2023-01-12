package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.domain.DefaultRoles
import br.com.zumbolovsky.fateapp.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService,
    private val jwtTokenFilter: JwtTokenFilter) {

    companion object SecurityConstants {
        internal val LOGGER: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)
        internal val HIERARCHY = "${DefaultRoles.ADMIN} > STAFF \n STAFF > ${DefaultRoles.USER}"
        internal const val SECURED_URLS = "/secured/**"
        internal const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
        internal const val ISSUER = "fateapp"
        internal const val EXPIRATION_TIME = 1000 * 60 * 30
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
                it.requestMatchers(SECURED_URLS).authenticated()
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

    @Bean
    fun roleHierarchy(): RoleHierarchy =
        RoleHierarchyImpl()
            .also {
                it.setHierarchy(HIERARCHY)
                LOGGER.info("Role hierarchy set to '$HIERARCHY'")
            }
}