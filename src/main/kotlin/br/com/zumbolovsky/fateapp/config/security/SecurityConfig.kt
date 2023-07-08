package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.domain.DefaultRoles
import br.com.zumbolovsky.fateapp.service.UserService
import jakarta.servlet.http.HttpServletResponse
import liquibase.util.MD5Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity(
    prePostEnabled = true,
    jsr250Enabled = true,
    securedEnabled = true,
)
class SecurityConfig(
    private val userService: UserService,
    private val jwtTokenFilter: JwtTokenFilter,
) {

    companion object SecurityConstants {
        private val LOGGER: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)
        internal val SECURED_URLS = arrayOf("/fate/secured/**", "/fate/authorization/**")
        private const val HIERARCHY = "${DefaultRoles.ADMIN} > ${DefaultRoles.STAFF} \n ${DefaultRoles.STAFF} > ${DefaultRoles.USER}"
        internal const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
        internal const val ISSUER = "fateapp"
        //30 Minutes
        internal const val EXPIRATION_TIME = 1000 * 60 * 30

        @Bean
        fun roleHierarchy(): RoleHierarchy =
            RoleHierarchyImpl()
                .also {
                    it.setHierarchy(HIERARCHY)
                    LOGGER.info("Role hierarchy set to '$HIERARCHY'")
                }

        @Bean
        fun methodSecurityExpressionHandler(roleHierarchy: RoleHierarchy) : MethodSecurityExpressionHandler =
            DefaultMethodSecurityExpressionHandler().also {
                it.setRoleHierarchy(roleHierarchy)
                it.setDefaultRolePrefix(null)
            }
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
            .authorizeHttpRequests {
                it.requestMatchers(*SECURED_URLS).authenticated()
                    .anyRequest().permitAll()
            }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, exception ->
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        exception.message)
                }
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(authenticationProvider())
            .build()

    @Bean
    fun authenticationProvider() : AuthenticationProvider =
        object : AuthenticationProvider {
            override fun authenticate(authentication: Authentication): Authentication =
                userService.loadUserByUsername(authentication.name)
                    .let {
                        val username = authentication.name
                        val password = MD5Util.computeMD5(authentication.credentials.toString())
                        (if (it.username == username && it.password == password)
                            UsernamePasswordAuthenticationToken(username, password)
                        else null) as Authentication
                    }

            override fun supports(authentication: Class<*>): Boolean =
                authentication == UsernamePasswordAuthenticationToken::class.java

        }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager
}