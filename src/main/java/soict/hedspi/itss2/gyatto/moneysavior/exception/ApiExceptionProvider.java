package soict.hedspi.itss2.gyatto.moneysavior.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiExceptionProvider {
    public ApiException createTransactionNotFoundException(String transactionUuid) {
        return new ApiException(
                String.format("Transaction with UUID %s not found", transactionUuid),
                HttpStatus.NOT_FOUND
        );
    }

    public ApiException createCategoryNotFoundException(String name) {
        return new ApiException(
                String.format("Category with name %s not found", name),
                HttpStatus.NOT_FOUND
        );
    }
}
