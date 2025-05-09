package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class RecordTransactionRequest {
    @NotEmpty
    private String userUuid;
    @NotNull
    private TransactionType type;
    private String category;
    private String description;
    private BigDecimal amount;
}
