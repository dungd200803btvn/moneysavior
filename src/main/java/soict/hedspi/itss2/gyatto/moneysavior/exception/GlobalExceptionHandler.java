package soict.hedspi.itss2.gyatto.moneysavior.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApiException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getStatus().value(), e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // Field name
            String errorMessage = error.getDefaultMessage();    // Custom message
            errorMessages.add(fieldName + ": " + errorMessage);
        });
        return ResponseEntity.badRequest()
                .body(new ErrorDetails(HttpStatus.BAD_REQUEST.value(), String.join("; ", errorMessages)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
