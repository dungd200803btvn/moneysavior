package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIRequest {
    private String model;
    private String input;
}
