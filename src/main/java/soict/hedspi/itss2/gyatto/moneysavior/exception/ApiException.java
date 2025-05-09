package soict.hedspi.itss2.gyatto.moneysavior.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException {
    private HttpStatus status;

    public ApiException (String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public ApiException (String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
