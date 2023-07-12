package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.APIException
import br.com.zumbolovsky.fateapp.config.error.ErrorCode
import br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.ILLEGAL_ARGUMENT
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.INTERNAL_ERROR
import br.com.zumbolovsky.fateapp.config.error.ErrorStatus
import org.springframework.http.HttpStatus

@ErrorCode(
    debugMessage = ILLEGAL_ARGUMENT,
    message = INTERNAL_ERROR)
@ErrorStatus(HttpStatus.BAD_REQUEST)
class IllegalArgumentException(
    args: List<String>? = null,
    debugArgs: List<String>? = null
) : APIException(args, debugArgs) {

    companion object {
        private const val serialVersionUID: Long = 1310174543823539438L
    }
}