package soict.hedspi.itss2.gyatto.moneysavior.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private String message;
}
