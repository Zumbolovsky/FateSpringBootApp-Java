package br.com.zumbolovsky.fateapp.config.error;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
    String debugMessage() default "";
    String message() default "";
}
