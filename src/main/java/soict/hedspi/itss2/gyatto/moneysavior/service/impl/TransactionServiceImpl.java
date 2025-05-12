package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CommentOnTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.*;
import soict.hedspi.itss2.gyatto.moneysavior.entity.ChatHistory;
import soict.hedspi.itss2.gyatto.moneysavior.entity.ExpenseCategory;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;
import soict.hedspi.itss2.gyatto.moneysavior.exception.ApiException;
import soict.hedspi.itss2.gyatto.moneysavior.repository.ChatHistoryRepository;
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
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public RecordTransactionResponse recordTransaction(RecordTransactionRequest request) {
        var transaction = saveTransaction(request);

        if (transaction == null) {
            return RecordTransactionResponse.builder()
                    .transaction(null)
                    .comment("Ghi nhận giao dịch thất bại!")
                    .build();
        }

        return RecordTransactionResponse.builder()
                .transaction(request)
                .comment("Đã ghi nhận giao dịch thành công!")
                .build();
    }

    @Override
    public RecordTransactionResponse recordTransactionAuto(RecordTransactionAutoRequest request) {
        var userChat = ChatHistory.builder()
                .userUuid(request.getUserUuid())
                .message(request.getMessage())
                .sender(ChatHistory.Sender.USER)
                .build();

        var categories = expenseCategoryRepository.findAll();
        var categoryNames = categories.stream().map(ExpenseCategory::getName).toList();
        var prompt = new CategorizeTransactionPrompt(request.getMessage(), categoryNames);
        var result = chatbotService.categorizeTransaction(prompt);

        result.setUserUuid(request.getUserUuid());
        var transaction = saveTransaction(result);

        String comment = null;
        ChatHistory botChat = null;

        if (transaction == null) {
            comment = "Xin lỗi, tôi không nắm được nội dung mà bạn cung cấp. Bạn có thể nói rõ hơn không?";
            botChat = ChatHistory.builder()
                    .userUuid(request.getUserUuid())
                    .message(comment)
                    .sender(ChatHistory.Sender.BOT)
                    .build();
        } else {
            comment = getCommentOnNewestTransaction(request.getUserUuid()).getComment();
            botChat = ChatHistory.builder()
                    .userUuid(request.getUserUuid())
                    .message(comment)
                    .sender(ChatHistory.Sender.BOT)
                    .transaction(transaction)
                    .build();
            userChat.setTransaction(transaction);
        }

        chatHistoryRepository.saveAll(List.of(userChat, botChat));

        return RecordTransactionResponse.builder()
                .transaction(result)
                .comment(comment)
                .build();
    }

    private Transaction saveTransaction(RecordTransactionRequest request) {
        switch (request.getType()) {
            case EXPENSE -> {
                var category = expenseCategoryRepository.findAll().stream()
                        .filter(c -> c.getName().equals(request.getCategory()))
                        .findFirst()
                        .orElseThrow(() -> new ApiException("Category not found", HttpStatus.NOT_FOUND));
                var transaction = Transaction.builder()
                        .userUuid(request.getUserUuid())
                        .type(request.getType())
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .category(category)
                        .build();
                return transactionRepository.save(transaction);
            }
            case INCOME -> {
                var transaction = Transaction.builder()
                        .userUuid(request.getUserUuid())
                        .type(request.getType())
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .build();
                return transactionRepository.save(transaction);
            }
            default -> {
                log.warn("Transaction is undefined: {}", request.getDescription());
                return null;
            }
        }
    }

    @Override
    public GetCommentOnNewestTransactionResponse getCommentOnNewestTransaction(String userUuid) {
        var latestTransaction = transactionRepository.findFirstByUserUuidOrderByTimestampDesc(userUuid);
        if (latestTransaction == null) {
            return GetCommentOnNewestTransactionResponse.builder()
                    .comment("Bạn chưa có giao dịch nào!")
                    .build();
        }

        var currentDate = LocalDate.now();
        var startDate = currentDate.withDayOfMonth(1);
        var endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        var totalIncome = transactionRepository.findTotalIncomeByUserUuid(userUuid, startDate, endDate);
        var categorySummaryResults = transactionRepository.findCategorySummaryByUserUuid(userUuid, startDate, endDate);
        var prompt = new CommentOnTransactionPrompt(latestTransaction, totalIncome, categorySummaryResults);
        var comment = chatbotService.getResponse(prompt);

        return GetCommentOnNewestTransactionResponse.builder()
                .comment(comment)
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
