package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.*;
import soict.hedspi.itss2.gyatto.moneysavior.feign.OpenAIFeignClient;
import soict.hedspi.itss2.gyatto.moneysavior.service.ChatbotService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIChatbotServiceImpl implements ChatbotService {
    private final OpenAIFeignClient openAIFeignClient;
    private final OpenAIRequestFactory openAIRequestFactory;
    private final ObjectMapper objectMapper;

    @Override
    public CategorizeTransactionResult categorizeTransaction(CategorizeTransactionPrompt prompt) {
        var text = getResponse(prompt);
        text = text.substring(text.indexOf('{'), text.lastIndexOf('}') + 1);
        log.info("Response from OpenAI: {}", text);
        try {
            return objectMapper.readValue(text, CategorizeTransactionResult.class);
        } catch (Exception e) {
            log.error("Failed to parse response from OpenAI: {}", e.getMessage());
            throw new RuntimeException("Failed to parse response from OpenAI", e);
        }
    }

    @Override
    public String getResponse(Prompt prompt) {
        var request = openAIRequestFactory.createRequest(prompt);
        var response = openAIFeignClient.getResponse(request);
        return response.getText();
    }
}
