package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import lombok.Data;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class RecordTransactionResult {
    private TransactionType type;
    private String category;
    private BigDecimal amount;
}
