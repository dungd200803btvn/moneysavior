package soict.hedspi.itss2.gyatto.moneysavior.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.GetCommentOnNewestTransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionAutoRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<RecordTransactionResponse> recordTransaction(@RequestBody RecordTransactionRequest request) {
        return ResponseEntity.ok(transactionService.recordTransaction(request));
    }

    @PostMapping(":auto")
    public ResponseEntity<RecordTransactionResponse> recordTransactionAuto(@RequestBody RecordTransactionAutoRequest request) {
        return ResponseEntity.ok(transactionService.recordTransactionAuto(request));
    }

    @GetMapping(":comment")
    public ResponseEntity<GetCommentOnNewestTransactionResponse> getCommentOnNewestTransaction(@RequestParam String userUuid) {
        return ResponseEntity.ok(transactionService.getCommentOnNewestTransaction(userUuid));
    }
}
