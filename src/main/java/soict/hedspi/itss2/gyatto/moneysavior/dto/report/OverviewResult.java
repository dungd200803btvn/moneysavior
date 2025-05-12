package soict.hedspi.itss2.gyatto.moneysavior.dto.report;

import java.math.BigDecimal;

public interface OverviewResult {
    BigDecimal getTotalIncomes();
    BigDecimal getTotalExpenses();
}
