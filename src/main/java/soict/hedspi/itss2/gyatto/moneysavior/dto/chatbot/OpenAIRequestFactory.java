package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAIRequestFactory {

    @Value("${openai.api.model}")
    private String defaultModel;

    public OpenAIRequest createRequest(Prompt prompt) {
        return createRequest(prompt.toString());
    }

    public OpenAIRequest createRequest(String input) {
        return OpenAIRequest.builder()
                .model(defaultModel)
                .input(input)
                .build();
    }
}