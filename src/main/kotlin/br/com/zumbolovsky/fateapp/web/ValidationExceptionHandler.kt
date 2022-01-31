package br.com.zumbolovsky.fateapp.web

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.stream.Collectors
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ValidationExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        val body: MutableMap<String, List<String?>> = HashMap()
        val errors = ex.bindingResult
            .fieldErrors
            .stream()
            .map { obj: FieldError -> obj.defaultMessage }
            .collect(Collectors.toList())
        body["errors"] = errors
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(ex: ConstraintViolationException,
                                     request: WebRequest): ResponseEntity<*> {
        val errors: MutableList<String> = ArrayList()
        ex.constraintViolations.forEach { cv -> errors.add(cv.message) }
        val result: MutableMap<String, List<String>> = HashMap()
        result["errors"] = errors
        return ResponseEntity<Map<String, List<String>>>(result, HttpStatus.BAD_REQUEST)
    }
}