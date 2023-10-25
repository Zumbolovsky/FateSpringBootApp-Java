package br.com.zumbolovsky.fateapp.service;

import br.com.zumbolovsky.fateapp.config.error.APIException;
import br.com.zumbolovsky.fateapp.config.error.ErrorCode;
import br.com.zumbolovsky.fateapp.config.error.ErrorStatus;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.List;

import static br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.ENTITY_EXISTS;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.INTERNAL_ERROR;

@ErrorCode(
        debugMessage = ENTITY_EXISTS,
        message = INTERNAL_ERROR)
@ErrorStatus(HttpStatus.CONFLICT)
public class EntityExistsException extends APIException {

    @Serial
    private static final long serialVersionUID = 1310174543823539438L;

    public EntityExistsException(List<String> args,
                                 List<String> debugArgs) {
        super(args, debugArgs);
    }
    public EntityExistsException(List<String> debugArgs) {
        super(null, debugArgs);
    }
    public EntityExistsException() {
        super(null, null);
    }
}