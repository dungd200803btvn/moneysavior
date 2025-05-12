package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateTransactionRequest {
    @NotNull
    private TransactionType type;
    @NotEmpty
    private String category;
    @NotEmpty
    private String description;
    @NotNull
    @Min(0)
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDate date;
}
