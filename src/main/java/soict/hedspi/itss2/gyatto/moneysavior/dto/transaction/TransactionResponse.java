package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String uuid;
    private TransactionType type;
    private String category;
    private String description;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;
}
