package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.OpenAIRequestFactory;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.RecordTransactionPrompt;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.RecordTransactionResult;
import soict.hedspi.itss2.gyatto.moneysavior.feign.OpenAIFeignClient;
import soict.hedspi.itss2.gyatto.moneysavior.service.ChatbotService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIChatbotServiceImpl implements ChatbotService {
    private final OpenAIFeignClient openAIFeignClient;
    private final OpenAIRequestFactory openAIRequestFactory;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void test() {
        var result = recordTransaction(new RecordTransactionPrompt("ăn bún đậu mắm tôm 30k", List.of("Nhà ở", "Đi lại", "Ăn uống", "Mua sắm", "Giải trí", "Giáo dục", "Sức khỏe", "Khác")));
        log.info("Test result: {}", result);
    }

    @Override
    public RecordTransactionResult recordTransaction(RecordTransactionPrompt prompt) {
        var request = openAIRequestFactory.createRequest(prompt.toString());
        var response = openAIFeignClient.getResponse(request);
        var text = response.getOutput().get(0).getContent().get(0).getText();
        log.info("Response from OpenAI: {}", text);
        try {
            return objectMapper.readValue(text, RecordTransactionResult.class);
        } catch (Exception e) {
            log.error("Failed to parse response from OpenAI: {}", e.getMessage());
            throw new RuntimeException("Failed to parse response from OpenAI", e);
        }
    }
}
