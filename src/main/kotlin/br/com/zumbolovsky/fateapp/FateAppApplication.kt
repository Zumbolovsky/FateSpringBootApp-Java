package br.com.zumbolovsky.fateapp

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.http.HttpHeaders

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableAutoConfiguration
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
            runApplication<FateAppApplication>(*args)
        }
    }
}