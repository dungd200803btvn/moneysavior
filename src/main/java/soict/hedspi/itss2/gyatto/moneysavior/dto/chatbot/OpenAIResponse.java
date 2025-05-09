package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {
    private List<Output> output;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Output {
        private List<Content> content;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String type;
        private String text;
    }
}
