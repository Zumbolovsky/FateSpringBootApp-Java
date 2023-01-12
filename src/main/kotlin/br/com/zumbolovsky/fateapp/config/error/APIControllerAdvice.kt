package br.com.zumbolovsky.fateapp.config.error

import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ARGUMENT_NOT_VALID
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ENTITY_EXISTS_DEFAULT
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ENTITY_NOT_FOUND_DEFAULT
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.MESSAGE_NOT_READABLE
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.UNAUTHORIZED
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.UNKNOWN
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class APIControllerAdvice(
    private val accessor: ErrorMessageSourceAccessor
) {
    companion object {
        internal val logger: Logger = LoggerFactory.getLogger(APIControllerAdvice::class.java)
    }

    init {
        logger.info("Creating ApplicationControllerAdvice...")
    }

    @ResponseBody
    @ExceptionHandler(APIException::class)
    fun handleAPIException(e: APIException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = getMessage(e),
                debugMessage = getDebugMessage(e),
                additionalInfo = e.additionalInfo),
            getHttpStatus(e))

    private fun getHttpStatus(e: APIException): HttpStatus =
        e.status.also { logger.info("Status: $it") }

    private fun getMessage(e: APIException): String =
        createMessage(e.message, e.args, e.messages).also { logger.info("Message: $it") }

    private fun getDebugMessage(e: APIException): String =
        createMessage(e.debugMessage, e.debugArgs, e.messages).also { logger.info("Debug message: $it") }

    private fun createMessage(errorCode: String?, args: List<Any>, messages: ArrayList<String>): String {
        return if (errorCode != null) accessor.getMessage(errorCode, accessor.convertArgs(args))
        else messages.joinToString()
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(MESSAGE_NOT_READABLE),
                debugMessage = e.localizedMessage),
            HttpStatus.BAD_REQUEST)

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(ARGUMENT_NOT_VALID),
                debugMessage = e.fieldErrors.map { it.defaultMessage }.joinToString(" ")),
            HttpStatus.BAD_REQUEST)

    @ResponseBody
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: BadCredentialsException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(UNAUTHORIZED),
                debugMessage = e.localizedMessage),
            HttpStatus.UNAUTHORIZED)

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(e: EntityNotFoundException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(ENTITY_NOT_FOUND_DEFAULT),
                debugMessage = e.localizedMessage),
            HttpStatus.NOT_FOUND)

    @ResponseBody
    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExists(e: EntityExistsException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(ENTITY_EXISTS_DEFAULT),
                debugMessage = e.localizedMessage),
            HttpStatus.CONFLICT)

    @ResponseBody
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntime(e: RuntimeException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage(UNKNOWN),
                debugMessage = e.localizedMessage),
            HttpStatus.NOT_FOUND)
}
