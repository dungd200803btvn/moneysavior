package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RecordTransactionAutoRequest {
    @NotEmpty
    private String userUuid;
    @NotEmpty
    private String message;
}
