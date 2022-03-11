package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService,
    private val jwtTokenFilter: JwtTokenFilter) {

    companion object SecurityConstants {
        const val SIGN_UP_URL = "/users"
        val ALLOWED_URLS = arrayOf(
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**",
            "/login")
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
                it.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                    .antMatchers(*ALLOWED_URLS).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()


    @Bean
    fun localeResolver(): LocaleResolver =
         SessionLocaleResolver().also {
            it.setDefaultLocale(Locale.US)
        }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor =
        LocaleChangeInterceptor().also {
            it.paramName = "lang"
        }

    @Bean
    fun authenticationManager(): AuthenticationManager =
        AuthenticationManager { auth ->
            userService.loadByUsernameAndPassword(auth.name, auth.credentials as String)
                .let { auth }
        }
}