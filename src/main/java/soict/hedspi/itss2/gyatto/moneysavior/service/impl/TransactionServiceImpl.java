package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.*;
import soict.hedspi.itss2.gyatto.moneysavior.entity.ExpenseCategory;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;
import soict.hedspi.itss2.gyatto.moneysavior.repository.ExpenseCategoryRepository;
import soict.hedspi.itss2.gyatto.moneysavior.repository.TransactionRepository;
import soict.hedspi.itss2.gyatto.moneysavior.service.ChatbotService;
import soict.hedspi.itss2.gyatto.moneysavior.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final ChatbotService chatbotService;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public RecordTransactionResponse recordTransaction(RecordTransactionRequest request) {
        var categories = expenseCategoryRepository.findAll();
        switch (request.getType()) {
            case EXPENSE -> {
                var category = categories.stream()
                        .filter(c -> c.getName().equals(request.getCategory()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                var transaction = Transaction.builder()
                        .userUuid(request.getUserUuid())
                        .type(request.getType())
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .category(category)
                        .build();
                transactionRepository.save(transaction);
            }
            case INCOME -> {
                var transaction = Transaction.builder()
                        .userUuid(request.getUserUuid())
                        .type(request.getType())
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .build();
                transactionRepository.save(transaction);
            }
            case UNDEFINED -> {
                log.warn("Transaction is undefined: {}", request.getDescription());
                return RecordTransactionResponse.builder()
                        .comment("Xin lỗi, tôi không nắm được nội dung mà bạn cung cấp. Bạn có thể nói rõ hơn không?")
                        .build();
            }
        }

        var comment = getCommentOnNewestTransaction(request.getUserUuid()).getComment();

        return RecordTransactionResponse.builder()
                .transaction(request)
                .comment(comment)
                .build();
    }

    @Override
    public RecordTransactionResponse recordTransactionAuto(RecordTransactionAutoRequest request) {
        var categories = expenseCategoryRepository.findAll();
        var categoryNames = categories.stream().map(ExpenseCategory::getName).toList();
        var prompt = new CategorizeTransactionPrompt(request.getMessage(), categoryNames);
        var result = chatbotService.categorizeTransaction(prompt);

        result.setUserUuid(request.getUserUuid());
        return recordTransaction(result);
    }

    @Override
    public GetCommentOnNewestTransactionResponse getCommentOnNewestTransaction(String userUuid) {
        // TODO: Implement this method

        return GetCommentOnNewestTransactionResponse.builder()
                .comment("Ghi nhận thành công giao dịch của bạn")
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
