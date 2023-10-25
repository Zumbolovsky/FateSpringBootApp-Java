package br.com.zumbolovsky.fateapp.config.error;

import org.springframework.http.HttpStatus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorStatus {
    HttpStatus value();
}
