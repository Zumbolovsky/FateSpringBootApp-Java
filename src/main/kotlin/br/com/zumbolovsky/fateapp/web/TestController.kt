package br.com.zumbolovsky.fateapp.web

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    @Operation(summary = "Test")
    fun test(): String {
        return "Hello World"
    }
}