package br.com.zumbolovsky.fateapp.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Aspect
@Component
public class TimeoutAspect {

    @Around("@annotation(br.com.zumbolovsky.fateapp.service.Timeout) && @annotation(timeout)")
    public Object around(ProceedingJoinPoint pjp, Timeout timeout) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> {
            try {
                return (String) pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
        try {
            return future.get(timeout.value(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new RuntimeException("Timeout!", e);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdownNow();
        }
    }
}