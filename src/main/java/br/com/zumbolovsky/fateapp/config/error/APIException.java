package br.com.zumbolovsky.fateapp.config.error;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class APIException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -4014775134653608283L;
    private final ArrayList<String> messages = new ArrayList<>();
    private final HashMap<String, Object> additionalInfo = new HashMap<>();
    private ArrayList<Object> debugArgs = new ArrayList<>();
    private ArrayList<Object> args = new ArrayList<>();

    public APIException(List<String> args, List<String> debugArgs) {
        if (args == null) {
            this.args = null;
        } else {
            addArguments(args);
        }
        if (args == null) {
            this.debugArgs = null;
        } else {
            addDebugArguments(debugArgs);
        }
    }


    public String getDebugMessage() {
        Class<? extends APIException> klass = this.getClass();
        return klass.isAnnotationPresent(ErrorCode.class) ?
                klass.getAnnotation(ErrorCode.class).debugMessage() :
                null;
    }

    public String getMessage() {
        Class<? extends APIException> klass = this.getClass();
        return klass.isAnnotationPresent(ErrorCode.class) ?
                klass.getAnnotation(ErrorCode.class).message() :
                null;
    }

    public HttpStatus getStatus() {
        Class<? extends APIException> klass = this.getClass();
        return klass.isAnnotationPresent(ErrorStatus.class) ?
            klass.getAnnotation(ErrorStatus.class).value() :
            HttpStatus.BAD_REQUEST;
    }

    public ArrayList<Object> getArgs() {
        return args;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public ArrayList<Object> getDebugArgs() {
        return debugArgs;
    }

    public HashMap<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    private void addDebugArguments(List<String> values) {
        debugArgs.addAll(values);
    }

    private void addArguments(List<String> values) {
        args.addAll(values);
    }

    public void addAditionalInfo(String key, Object value) {
        additionalInfo.put(key, value);
    }

}