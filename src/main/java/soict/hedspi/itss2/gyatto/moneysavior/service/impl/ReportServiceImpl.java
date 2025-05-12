package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetCategorySummaryRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetOverviewRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetOverviewResponse;
import soict.hedspi.itss2.gyatto.moneysavior.repository.TransactionRepository;
import soict.hedspi.itss2.gyatto.moneysavior.service.ReportService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final TransactionRepository transactionRepository;

    @Override
    public GetOverviewResponse getOverview(GetOverviewRequest request) {
        var result = transactionRepository.findOverviewByUserUuid(
                request.getUserUuid(),
                request.getStartDate(),
                request.getEndDate()
        );
        return GetOverviewResponse.builder()
                .balance(result.getTotalIncomes().subtract(result.getTotalExpenses()))
                .totalIncomes(result.getTotalIncomes())
                .totalExpenses(result.getTotalExpenses())
                .build();
    }

    @Override
    public List<CategorySummaryResult> getCategorySummary(GetCategorySummaryRequest request) {
        return transactionRepository.findCategorySummaryByUserUuid(
                request.getUserUuid(),
                request.getStartDate(),
                request.getEndDate()
        );
    }
}
