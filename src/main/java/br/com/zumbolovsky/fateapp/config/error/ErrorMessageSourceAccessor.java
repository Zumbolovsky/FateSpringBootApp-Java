package br.com.zumbolovsky.fateapp.config.error;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

public class ErrorMessageSourceAccessor {

    private final MessageSourceAccessor messageSourceAccessor;

    public ErrorMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public String getMessage(String code, Object... args) {
        return messageSourceAccessor.getMessage(code, args);
    }

    public Object[] convertArgs(List<Object> args) {
        return ObjectUtils.isEmpty(args) ?
                new Object[]{} :
                args.stream()
                        .map(arg -> (arg instanceof String) ?
                                messageSourceAccessor.getMessage(arg.toString(), arg.toString()) :
                                arg)
                        .toArray();
    }

    public String getMessage(String code) {
        return messageSourceAccessor.getMessage(code);
    }
}