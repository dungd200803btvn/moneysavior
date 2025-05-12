package soict.hedspi.itss2.gyatto.moneysavior.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.GetCommentOnNewestTransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionAutoRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transactions")
    @Operation(
            summary = "Nhập giao dịch thủ công",
            description = "type là 'EXPENSE' hoặc 'INCOME'. \n" +
                    "Nếu là 'EXPENSE' thì category thuộc 1 trong \"Nhà ở\", \"Đi lại\", \"Ăn uống\", \"Mua sắm\", \"Giải trí\", \"Giáo dục\", \"Sức khỏe\", \"Khác\". \n" +
                    "Nếu là 'INCOME' thì category là null.")
    public ResponseEntity<RecordTransactionResponse> recordTransaction(@RequestBody @Valid RecordTransactionRequest request) {
        return ResponseEntity.ok(transactionService.recordTransaction(request));
    }

    @PostMapping("/transactions:auto")
    @Operation(
            summary = "Nhập giao dịch tự động qua bot",
            description = "message là nội dung ghi thu/chi, ví dụ: \"Mua đồ ăn 200k\" hoặc \"Nhận lương 5tr\".")
    public ResponseEntity<RecordTransactionResponse> recordTransactionAuto(@RequestBody @Valid RecordTransactionAutoRequest request) {
        return ResponseEntity.ok(transactionService.recordTransactionAuto(request));
    }

    @GetMapping("/transactions:comment")
    @Operation(summary = "Đưa ra nhận xét cho giao dịch gần nhất (đã tích hợp trong api ghi giao dịch tự động)")
    public ResponseEntity<GetCommentOnNewestTransactionResponse> getCommentOnNewestTransaction(@RequestParam String userUuid) {
        return ResponseEntity.ok(transactionService.getCommentOnNewestTransaction(userUuid));
    }
}
