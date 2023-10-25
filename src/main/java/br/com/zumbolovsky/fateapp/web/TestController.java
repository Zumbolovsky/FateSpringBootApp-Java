package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.config.error.APIResponse;
import br.com.zumbolovsky.fateapp.domain.redis.Test;
import br.com.zumbolovsky.fateapp.service.KotlinAppService;
import br.com.zumbolovsky.fateapp.service.RedisService;
import br.com.zumbolovsky.fateapp.service.TestEnum;
import br.com.zumbolovsky.fateapp.service.Timeout;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RestController
class TestController {

    private final KotlinAppService kotlinAppService;
    private final RedisService redisService;

    public TestController(KotlinAppService kotlinAppService, RedisService redisService) {
        this.kotlinAppService = kotlinAppService;
        this.redisService = redisService;
    }

    @GetMapping("/test")
    @Operation(summary = "Test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/executor/service/test")
    @Operation(summary = "Test of executor service")
    public String testExecutorService() {
        var executorService = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> listOf = List.of(
                () -> {throw new RuntimeException();},
                () -> 9,
                () -> 8,
                () -> 7,
                () -> 6,
                () -> 5,
                () -> 4,
                () -> 3,
                () -> 2,
                () -> 1
        );
        try {
            var futures = executorService.invokeAll(listOf);
            while (!futures.stream().allMatch(Future::isDone)) {
                Thread.sleep(1000);
            }
            executorService.shutdown();
            return futures.stream()
                .map(it -> {
                    try {
                        return it.get().toString();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return "F1";
                    } catch(ExecutionException e){
                        return "F2";
                    }
                }).collect(Collectors.joining(","));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/timeout/aspect/test")
    @Operation(summary = "Testing aspect implemented timeout")
    @Timeout
    public String aspectTimeoutTest() {
        return timeoutTest();
    }

    //Inconsistent with close timeout and execution durations
    @GetMapping("/timeout/spring/test")
    @Operation(summary = "Testing spring default implementation timeout")
    public Callable<String> springTimeoutTest() {
        return this::timeoutTest;
    }

    private String timeoutTest() {
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello World";
    }

    @GetMapping("/plugin/test/{processor}")
    @Operation(summary = "Testing spring plugin registry")
    public APIResponse<String> springPluginRegistryTest(@PathVariable("processor") TestEnum processor) {
        return new APIResponse<>(kotlinAppService.testPluginRegistry(processor));
    }

    @GetMapping("/test/redis")
    @Operation(summary = "Testing find all in Redis as database")
    public APIResponse<List<Test>> findAllRedis() {
        return new APIResponse<>(redisService.findAll());
    }

    @GetMapping("/test/redis/{id}")
    @Operation(summary = "Testing find by id in Redis as database")
    public APIResponse<Test> findByIdRedis(@PathVariable("id") Integer id) {
        return new APIResponse<>(redisService.findById(id));
    }

    @PostMapping("/test/redis")
    @Operation(summary = "Testing save in Redis as database")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<Test> saveRedis(@RequestBody Test test) {
        return new APIResponse<>(redisService.save(test));
    }

    @DeleteMapping("/test/redis/{id}")
    @Operation(summary = "Testing delete by id in Redis as database")
    public void deleteByIdRedis(@PathVariable("id") Integer id) {
        redisService.deleteById(id);
    }
}
