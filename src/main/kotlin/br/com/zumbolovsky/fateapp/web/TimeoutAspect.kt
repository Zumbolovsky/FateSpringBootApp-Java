package br.com.zumbolovsky.fateapp.web

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@Aspect
@Component
class TimeoutAspect {

    @Around("@annotation(br.com.zumbolovsky.fateapp.web.Timeout) && @annotation(timeout)")
    fun around(pjp: ProceedingJoinPoint, timeout: Timeout): Any {
        val executor = Executors.newSingleThreadExecutor()
        val future: Future<Any> = executor.submit<Any> { pjp.proceed() as String }
        return try {
            future.get(timeout.value, TimeUnit.MILLISECONDS)
        } catch (e: TimeoutException) {
            future.cancel(true)
            throw RuntimeException("Timeout!", e)
        } finally {
            executor.shutdownNow()
        }
    }
}