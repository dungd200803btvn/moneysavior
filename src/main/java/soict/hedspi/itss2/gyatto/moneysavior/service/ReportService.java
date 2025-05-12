package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.report.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetCategorySummaryRequest;

import java.util.List;

public interface ReportService {
    List<CategorySummaryResult> getCategorySummary(GetCategorySummaryRequest request);
}
