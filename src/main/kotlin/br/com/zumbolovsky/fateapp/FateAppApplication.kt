package br.com.zumbolovsky.fateapp

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class FateAppApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<FateAppApplication>(*args)
        }
    }
}