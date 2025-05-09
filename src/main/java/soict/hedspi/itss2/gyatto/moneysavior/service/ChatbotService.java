package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.CategorizeTransactionResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.Prompt;

public interface ChatbotService {
    CategorizeTransactionResult categorizeTransaction(CategorizeTransactionPrompt prompt);

    String getResponse(Prompt prompt);
}
