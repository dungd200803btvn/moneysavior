package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.RecordTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.RecordTransactionResult;

public interface ChatbotService {
    RecordTransactionResult recordTransaction(RecordTransactionPrompt prompt);
}
