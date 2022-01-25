package br.com.zumbolovsky.fateapp.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    fun fateOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Fate API")
                    .description("API para Fate/GO")
                    .version("v0.0.1")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0"))
                    .contact(
                        Contact().name("Andrew Siquieri")))
    }
}