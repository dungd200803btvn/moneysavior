package soict.hedspi.itss2.gyatto.moneysavior.dto.transaction;

import java.math.BigDecimal;

public interface CategorySummaryResult {
    String getCategoryName();

    BigDecimal getTotalAmount();

    Double getPercentage();
}
