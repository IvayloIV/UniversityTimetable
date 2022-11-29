package bg.tuvarna.universitytimetable.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

    private final String viewName;
    private Map<String, Object> models;

    public ValidationException(String message, String viewName, Map<String, Object> models) {
        super(message);
        this.viewName = viewName;
        this.models = models;
    }

    public ValidationException(String message, String viewName) {
        super(message);
        this.viewName = viewName;
    }
}
