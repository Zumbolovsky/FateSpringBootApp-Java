package br.com.zumbolovsky.fateapp.config.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class APIResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7842207334955640260L;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private T body = null;
    private String message = null;
    private String debugMessage = null;
    private HashMap<String, Object> additionalInfo = new HashMap<>();

    public APIResponse(LocalDateTime timestamp, T body, String message, String debugMessage, HashMap<String, Object> additionalInfo) {
        this.timestamp = timestamp;
        this.body = body;
        this.message = message;
        this.debugMessage = debugMessage;
        this.additionalInfo = additionalInfo;
    }

    public APIResponse(String message, String debugMessage, HashMap<String, Object> additionalInfo) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.additionalInfo = additionalInfo;
    }

    public APIResponse(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public APIResponse(T body) {
        this.body = body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public T getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public HashMap<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }
}