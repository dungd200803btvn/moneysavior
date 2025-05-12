package soict.hedspi.itss2.gyatto.moneysavior.dto.report;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public abstract class QueryByUserAndTimeRangeRequest {
    private String userUuid;
    private LocalDate startDate;
    private LocalDate endDate;
}
