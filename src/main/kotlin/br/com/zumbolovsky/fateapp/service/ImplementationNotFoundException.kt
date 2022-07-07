package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.APIException
import br.com.zumbolovsky.fateapp.config.error.ErrorCode
import br.com.zumbolovsky.fateapp.config.error.ErrorStatus
import org.springframework.http.HttpStatus

@ErrorCode(
    debugMessage = "IMPLEMENTATION.NOT.FOUND",
    message = "INTERNAL.ERROR")
@ErrorStatus(HttpStatus.BAD_REQUEST)
class ImplementationNotFoundException(
    args: List<String>
) : APIException() {

    companion object {
        private const val serialVersionUID: Long = 1310174543823539438L
    }

    init {
        addArguments(args)
    }
}