package rest.bibliopole.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiError {

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonProperty(value = "HTTP status")
    private HttpStatus status;

    @JsonProperty(value = "Message")
    private String message;

    @JsonProperty(value = "Debug message")
    private String debugMessage;

    @JsonProperty(value = "Field Validation Errors")
    private List<FieldValidationError> fieldValidationErrors;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }


    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(error -> {
            FieldValidationError subError = new FieldValidationError();
            subError.setField(error.getField());
            subError.setMessage(error.getDefaultMessage());
            subError.setRejectedValue(error.getRejectedValue());
            subError.setObject(error.getObjectName());
            this.addSubError(subError);
        });
    }

    private void addSubError(FieldValidationError subError) {
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(subError);
    }
}
