package br.com.zumbolovsky.fateapp.config.error

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
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException

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
        e.status
            .also {
                logger.info("Status: $it")
            }

    private fun getMessage(e: APIException): String =
        createMessage(e.message, e.args, e.messages)

    private fun getDebugMessage(e: APIException): String =
        createMessage(e.debugMessage, e.debugArgs, e.messages)

    private fun createMessage(errorCode: String?, args: List<Any>, messages: ArrayList<String>): String =
        (if (errorCode != null)
            accessor.getMessage(errorCode, accessor.convertArgs(args))
        else messages.joinToString())
            .also {
                logger.info("Message: $it")
            }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("MESSAGE.NOT.READABLE"),
                debugMessage = e.localizedMessage),
            HttpStatus.BAD_REQUEST)

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("ARGUMENT.NOT.VALID"),
                debugMessage = e.fieldErrors.map { it.defaultMessage }.joinToString(" ")),
            HttpStatus.BAD_REQUEST)

    @ResponseBody
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: BadCredentialsException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("UNAUTHORIZED"),
                debugMessage = e.localizedMessage),
            HttpStatus.UNAUTHORIZED)

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(e: EntityNotFoundException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("ENTITY.NOT.FOUND"),
                debugMessage = e.localizedMessage),
            HttpStatus.NOT_FOUND)

    @ResponseBody
    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExists(e: EntityExistsException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("ENTITY.EXISTS"),
                debugMessage = e.localizedMessage),
            HttpStatus.CONFLICT)

    @ResponseBody
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntime(e: RuntimeException): ResponseEntity<APIResponse<Any>> =
        ResponseEntity(
            APIResponse(
                message = accessor.getMessage("UNKNOWN"),
                debugMessage = e.localizedMessage),
            HttpStatus.NOT_FOUND)
}
