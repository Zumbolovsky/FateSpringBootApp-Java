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
    fun test(): String {
        return "Hello World"
    }

    @GetMapping("/test-executor-service")
    @Operation(summary = "Test of executor service")
    fun testExecutorService(): String {
        val executorService = Executors.newFixedThreadPool(4)
        val listOf: List<Callable<Int>> = listOf(
            Callable {
                throw RuntimeException()
            },
            Callable {
                return@Callable 9
            },
            Callable {
                return@Callable 8
            },
            Callable {
                return@Callable 7
            },
            Callable {
                return@Callable 6
            },
            Callable {
                return@Callable 5
            },
            Callable {
                return@Callable 4
            },
            Callable {
                return@Callable 3
            },
            Callable {
                return@Callable 2
            },
            Callable {
                return@Callable 1
            })
        try {
            val futures = executorService.invokeAll(listOf)
            while (!futures.stream()
                    .allMatch { obj: Future<Int> -> obj.isDone }) {
                Thread.sleep(1000)
            }
            executorService.shutdown()
            return futures.stream()
                .map {
                    try {
                        return@map it.get().toString()
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                        return@map "F1"
                    } catch (e: ExecutionException) {
                        return@map "F2"
                    }
                }.collect(Collectors.joining(","))
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(e)
        }
    }

    @PostMapping("/test-mongo")
    @Operation(summary = "Testing mongo")
    fun insertMongo(): MainCharacter =
        mongoService.createMC()
}
