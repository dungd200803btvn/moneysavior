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
import soict.hedspi.itss2.gyatto.moneysavior.exception.ApiExceptionProvider;
import soict.hedspi.itss2.gyatto.moneysavior.mapper.TransactionMapper;
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
    private final ApiExceptionProvider apiExceptionProvider;
    private final TransactionMapper transactionMapper;

    @Override
    public RecordTransactionResponse recordTransaction(RecordTransactionRequest request) {
        var transaction = saveTransaction(request);

        if (transaction == null) {
            return RecordTransactionResponse.builder()
                    .transaction(null)
                    .comment("Ghi nhận giao dịch thất bại!")
                    .build();
        }

        var transactionResponse = transactionMapper.toTransactionResponse(transaction);

        return RecordTransactionResponse.builder()
                .transaction(transactionResponse)
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

        var recordTransactionRequest = RecordTransactionRequest.builder()
                .userUuid(request.getUserUuid())
                .type(result.getType())
                .category(result.getCategory())
                .description(result.getDescription())
                .amount(result.getAmount())
                .build();
        var transaction = saveTransaction(recordTransactionRequest);

        TransactionResponse transactionResponse = null;
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
            transactionResponse = transactionMapper.toTransactionResponse(transaction);
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
                .transaction(transactionResponse)
                .comment(comment)
                .build();
    }

    private Transaction saveTransaction(RecordTransactionRequest request) {
        switch (request.getType()) {
            case EXPENSE -> {
                var category = expenseCategoryRepository.findAll().stream()
                        .filter(c -> c.getName().equals(request.getCategory()))
                        .findFirst()
                        .orElseThrow(() -> apiExceptionProvider.createCategoryNotFoundException(request.getCategory()));
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
        var latestTransaction = transactionRepository.findFirstByUserUuidOrderByCreatedAtDesc(userUuid);
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
    public TransactionResponse updateTransaction(String uuid, UpdateTransactionRequest request) {
        var transaction = transactionRepository.findByUuid(uuid)
                .orElseThrow(() -> apiExceptionProvider.createTransactionNotFoundException(uuid));

        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        var category = expenseCategoryRepository.findAll().stream()
                .filter(c -> c.getName().equals(request.getCategory()))
                .findFirst()
                .orElseThrow(() -> apiExceptionProvider.createCategoryNotFoundException(request.getCategory()));
        transaction.setCategory(category);
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(GetTransactionHistoryRequest request) {
        return transactionRepository.findTransactionHistoryByUser(
                        request.getUserUuid(),
                        request.getType(),
                        request.getCategory(),
                        request.getYear(),
                        request.getMonth()
                )
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }
}
