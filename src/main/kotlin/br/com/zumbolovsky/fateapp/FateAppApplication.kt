package br.com.zumbolovsky.fateapp

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan(basePackages = ["br.com.zumbolovsky.fateapp"])
class FateAppApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<FateAppApplication>(*args)
        }
    }
}