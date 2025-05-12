package soict.hedspi.itss2.gyatto.moneysavior.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOverviewResponse implements OverviewResult{
    private BigDecimal balance;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
}
