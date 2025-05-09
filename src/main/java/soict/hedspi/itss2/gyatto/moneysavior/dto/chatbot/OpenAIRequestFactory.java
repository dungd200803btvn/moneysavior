package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAIRequestFactory {

    @Value("${openai.api.model}")
    private String defaultModel;

    public OpenAIRequest createRequest(String input) {
        return OpenAIRequest.builder()
                .model(defaultModel)
                .input(input)
                .build();
    }

    public OpenAIRequest createRequest(String model, String input) {
        return OpenAIRequest.builder()
                .model(model)
                .input(input)
                .build();
    }
}