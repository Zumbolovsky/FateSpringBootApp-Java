package br.com.zumbolovsky.fateapp.config.error;

import br.com.zumbolovsky.fateapp.service.EntityExistsException;
import br.com.zumbolovsky.fateapp.service.EntityNotFoundException;
import br.com.zumbolovsky.fateapp.service.ImplementationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ARGUMENT_NOT_VALID;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.BAD_CREDENTIALS;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ENTITY_EXISTS_DEFAULT;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ENTITY_NOT_FOUND_DEFAULT;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.IMPLEMENTATION_NOT_FOUND;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.MESSAGE_NOT_READABLE;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.UNAUTHORIZED;
import static br.com.zumbolovsky.fateapp.config.error.ErrorMessages.UNKNOWN;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class APIControllerAdvice {
    private final ErrorMessageSourceAccessor accessor;

    private static final Logger logger = LoggerFactory.getLogger(APIControllerAdvice.class);

    public APIControllerAdvice(ErrorMessageSourceAccessor accessor) {
        logger.info("Creating ApplicationControllerAdvice...");
        this.accessor = accessor;
    }

    @ResponseBody
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse<Object>> handleAPIException(APIException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        getMessage(e),
                        getDebugMessage(e),
                        e.getAdditionalInfo()),
                getHttpStatus(e));
    }

    private HttpStatus getHttpStatus(APIException e) {
        final HttpStatus status = e.getStatus();
        logger.info("Status: {}", status);
        return status;
    }

    private String getMessage(APIException e) {
        final String message = createMessage(e.getMessage(), e.getArgs(), e.getMessages());
        logger.info("Message: {}", message);
        return message;
    }

    private String getDebugMessage(APIException e) {
        final String message = createMessage(e.getDebugMessage(), e.getDebugArgs(), e.getMessages());
        logger.info("Debug message: {}", message);
        return message;
    }

    private String createMessage(String errorCode, List<Object> args, ArrayList<String> messages) {
        return errorCode != null ? accessor.getMessage(errorCode, accessor.convertArgs(args))
                : String.join("", messages);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(MESSAGE_NOT_READABLE),
                        e.getLocalizedMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<Object>> handleAccessDenied(AccessDeniedException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(UNAUTHORIZED),
                        e.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(ARGUMENT_NOT_VALID),
                        e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" "))),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<Object>> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(BAD_CREDENTIALS),
                        e.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(ENTITY_NOT_FOUND_DEFAULT),
                        e.getLocalizedMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<APIResponse<Object>> handleEntityExists(EntityExistsException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(ENTITY_EXISTS_DEFAULT),
                        e.getLocalizedMessage()),
                HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(ImplementationNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleImplementationNotFound(ImplementationNotFoundException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(IMPLEMENTATION_NOT_FOUND),
                        e.getLocalizedMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<Object>> handleRuntime(RuntimeException e) {
        return new ResponseEntity<>(
                new APIResponse<>(
                        accessor.getMessage(UNKNOWN),
                        e.getLocalizedMessage()),
                HttpStatus.NOT_FOUND);
    }
}
