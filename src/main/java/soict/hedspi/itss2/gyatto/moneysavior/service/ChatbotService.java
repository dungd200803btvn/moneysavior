package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionResult;

public interface ChatbotService {
    CategorizeTransactionResult categorizeTransaction(CategorizeTransactionPrompt prompt);
}
