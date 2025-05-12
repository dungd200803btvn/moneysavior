package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.report.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetCategorySummaryRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetOverviewRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetOverviewResponse;

import java.util.List;

public interface ReportService {
    GetOverviewResponse getOverview(GetOverviewRequest request);
    List<CategorySummaryResult> getCategorySummary(GetCategorySummaryRequest request);
}
