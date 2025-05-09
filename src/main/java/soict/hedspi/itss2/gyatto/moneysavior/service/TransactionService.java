package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.GetCommentOnNewestTransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionAutoRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.RecordTransactionResponse;

public interface TransactionService {
    RecordTransactionResponse recordTransaction(RecordTransactionRequest request);
    RecordTransactionResponse recordTransactionAuto(RecordTransactionAutoRequest request);
    GetCommentOnNewestTransactionResponse getCommentOnNewestTransaction(String userUuid);
}
