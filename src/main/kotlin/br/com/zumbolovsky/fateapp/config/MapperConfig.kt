package br.com.zumbolovsky.fateapp.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class MapperConfig {

    @Bean
    @Primary
    fun modelMapper(): ModelMapper = ModelMapper()
}