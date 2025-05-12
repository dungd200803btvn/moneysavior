package soict.hedspi.itss2.gyatto.moneysavior.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.GetCategorySummaryRequest;
import soict.hedspi.itss2.gyatto.moneysavior.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reports/category-summary")
    @Operation(
            summary = "Thống kê chi tiêu theo từng danh mục",
            description = "startDate và endDate định dạng yyyy-MM-dd (ví dụ: 2025-05-01). Trả về list thông số mỗi danh mục gồm categoryName (tên danh mục), totalAmount (tổng chi của danh mục đó) và percentage (phần trăm của danh mục đó so với tổng chi)."
    )
    public ResponseEntity<List<CategorySummaryResult>> getCategorySummary(
            @RequestParam String userUuid,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        var request = GetCategorySummaryRequest.builder()
                .userUuid(userUuid)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ResponseEntity.ok(reportService.getCategorySummary(request));
    }
}
