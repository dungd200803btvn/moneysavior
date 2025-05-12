package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTransactionHistoryRequest {
    private String userUuid;
    private TransactionType type;
    private String category;
    private int year;
    private int month;
}
