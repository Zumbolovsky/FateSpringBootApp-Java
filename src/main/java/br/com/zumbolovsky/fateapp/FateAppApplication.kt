package br.com.zumbolovsky.fateapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object FateAppApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(FateAppApplication::class.java, *args)
    }
}