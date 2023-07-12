package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.APIException
import br.com.zumbolovsky.fateapp.config.error.ErrorCode
import br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.ENTITY_EXISTS
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.INTERNAL_ERROR
import br.com.zumbolovsky.fateapp.config.error.ErrorStatus
import org.springframework.http.HttpStatus

@ErrorCode(
    debugMessage = ENTITY_EXISTS,
    message = INTERNAL_ERROR)
@ErrorStatus(HttpStatus.CONFLICT)
class EntityExistsException(
    args: List<String>? = null,
    debugArgs: List<String>? = null
) : APIException(args, debugArgs) {

    companion object {
        private const val serialVersionUID: Long = 1310174543823539438L
    }
}