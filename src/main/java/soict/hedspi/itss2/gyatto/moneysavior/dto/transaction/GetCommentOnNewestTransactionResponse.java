package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCommentOnNewestTransactionResponse {
    private String comment;
}
