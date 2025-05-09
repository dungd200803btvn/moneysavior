package soict.hedspi.itss2.gyatto.moneysavior.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.*;
import soict.hedspi.itss2.gyatto.moneysavior.service.TransactionService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<RecordTransactionResponse> recordTransaction(@RequestBody @Valid RecordTransactionRequest request) {
        return ResponseEntity.ok(transactionService.recordTransaction(request));
    }

    @PostMapping("/transactions:auto")
    public ResponseEntity<RecordTransactionResponse> recordTransactionAuto(@RequestBody @Valid RecordTransactionAutoRequest request) {
        return ResponseEntity.ok(transactionService.recordTransactionAuto(request));
    }

    @GetMapping("/transactions:comment")
    public ResponseEntity<GetCommentOnNewestTransactionResponse> getCommentOnNewestTransaction(@RequestParam String userUuid) {
        return ResponseEntity.ok(transactionService.getCommentOnNewestTransaction(userUuid));
    }

    @GetMapping("/transactions/reports/category-summary")
    public ResponseEntity<?> getCategorySummary(
            @RequestParam String userUuid,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        var request = GetCategorySummaryRequest.builder()
                .userUuid(userUuid)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ResponseEntity.ok(transactionService.getCategorySummary(request));
    }
}
