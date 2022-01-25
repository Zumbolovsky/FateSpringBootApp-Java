package br.com.zumbolovsky.fateapp.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class CryptoConfig {

    @Bean
    @Primary
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}