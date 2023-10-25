package br.com.zumbolovsky.fateapp.service;

import br.com.zumbolovsky.fateapp.config.error.APIException;
import br.com.zumbolovsky.fateapp.config.error.ErrorCode;
import br.com.zumbolovsky.fateapp.config.error.ErrorStatus;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.List;

import static br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.IMPLEMENTATION_NOT_FOUND;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.INTERNAL_ERROR;

@ErrorCode(
        debugMessage = IMPLEMENTATION_NOT_FOUND,
        message = INTERNAL_ERROR)
@ErrorStatus(HttpStatus.BAD_REQUEST)
public class ImplementationNotFoundException extends APIException {

    @Serial
    private static final long serialVersionUID = 1310174543823539438L;

    public ImplementationNotFoundException(List<String> args,
                                    List<String> debugArgs) {
        super(args, debugArgs);
    }

    public ImplementationNotFoundException(List<String> debugArgs) {
        super(null, debugArgs);
    }

    public ImplementationNotFoundException() {
        super(null, null);
    }

}