package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.service.TestProcessorService
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.http.HttpHeaders
import org.springframework.plugin.core.config.EnablePluginRegistries

@SpringBootApplication
@EnablePluginRegistries(TestProcessorService::class)
@EnableCaching
@EnableRedisRepositories(basePackages = ["br.com.zumbolovsky.fateapp.domain.redis"])
@EnableJpaRepositories(basePackages = ["br.com.zumbolovsky.fateapp.domain.postgres"])
@EnableAspectJAutoProxy
@ComponentScan(basePackages = ["br.com.zumbolovsky.fateapp"])
@OpenAPIDefinition(
    info = Info(
        title = "Fate API",
        description = "API para Fate/GO",
        version = "v0.0.1",
        license = License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        contact = Contact(name = "Andrew Siquieri")))
@SecurityScheme(
    name = HttpHeaders.AUTHORIZATION,
    scheme = "basic",
    type = SecuritySchemeType.APIKEY,
    `in` = SecuritySchemeIn.HEADER,
    bearerFormat = "JWT")
class FateAppApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<br.com.zumbolovsky.fateapp.FateAppApplication>(*args)
        }
    }
}