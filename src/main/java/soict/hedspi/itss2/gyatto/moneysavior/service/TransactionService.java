package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.*;

public interface TransactionService {
    RecordTransactionResponse recordTransaction(RecordTransactionRequest request);
    RecordTransactionResponse recordTransactionAuto(RecordTransactionAutoRequest request);
    GetCommentOnNewestTransactionResponse getCommentOnNewestTransaction(String userUuid);
    TransactionResponse updateTransaction(String uuid, UpdateTransactionRequest request);
}
