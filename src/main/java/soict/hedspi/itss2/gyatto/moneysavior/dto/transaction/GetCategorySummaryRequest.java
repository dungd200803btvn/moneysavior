package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCategorySummaryRequest {
    private String userUuid;
    private int year;
    private int month;
}
