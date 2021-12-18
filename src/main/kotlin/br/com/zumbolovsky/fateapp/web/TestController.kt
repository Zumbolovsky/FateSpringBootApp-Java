package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.MongoService
import br.com.zumbolovsky.fateapp.domain.MainCharacter
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.stream.Collectors

@RestController
class TestController {

    @Autowired private lateinit var mongoService: MongoService

    @GetMapping("/test")
    @Operation(summary = "Test")
    fun test(): String = "Hello World"

    @GetMapping("/executor/service/test")
    @Operation(summary = "Test of executor service")
    fun testExecutorService(): String {
        val executorService = Executors.newFixedThreadPool(4)
        val listOf: List<Callable<Int>> = listOf(
            Callable { throw RuntimeException() },
            Callable { 9 },
            Callable { 8 },
            Callable { 7 },
            Callable { 6 },
            Callable { 5 },
            Callable { 4 },
            Callable { 3 },
            Callable { 2 },
            Callable { 1 })
        try {
            val futures = executorService.invokeAll(listOf)
            while (!futures.stream().allMatch { obj -> obj.isDone }) {
                Thread.sleep(1000)
            }
            executorService.shutdown()
            return futures.stream()
                .map {
                    try {
                        it.get().toString()
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                        "F1"
                    } catch (e: ExecutionException) {
                        "F2"
                    }
                }.collect(Collectors.joining(","))
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(e)
        }
    }

    @PostMapping("/mongo/test")
    @Operation(summary = "Testing mongo")
    fun insertMongo(): MainCharacter = mongoService.createMC()

    @GetMapping("/timeout/aspect/test")
    @Operation(summary = "Testing aspect implemented timeout")
    @Timeout
    fun aspectTimeoutTest(): String = timeoutTest()

    //Inconsistent with close timeout and execution durations
    @GetMapping("/timeout/spring/test")
    @Operation(summary = "Testing spring default implementation timeout")
    fun springTimeoutTest(): Callable<String> = Callable(::timeoutTest)

    private fun timeoutTest(): String {
        Thread.sleep(5500)
        return "Hello World"
    }
}
