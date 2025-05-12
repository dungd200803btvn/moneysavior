package soict.hedspi.itss2.gyatto.moneysavior.dto.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class GetOverviewRequest extends QueryByUserAndTimeRangeRequest {
}
