package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.*;

import java.util.List;

public interface TransactionService {
    RecordTransactionResponse recordTransaction(RecordTransactionRequest request);
    RecordTransactionResponse recordTransactionAuto(RecordTransactionAutoRequest request);
    GetCommentOnNewestTransactionResponse getCommentOnNewestTransaction(String userUuid);
    List<CategorySummaryResult> getCategorySummary(GetCategorySummaryRequest request);
}
