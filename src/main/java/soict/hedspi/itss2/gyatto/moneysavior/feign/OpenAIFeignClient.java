package soict.hedspi.itss2.gyatto.moneysavior.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.OpenAIRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot.OpenAIResponse;

@FeignClient(name = "openai", url = "${openai.api.url}")
public interface OpenAIFeignClient {
    @PostMapping(value = "/v1/responses", consumes = "application/json", headers = "Authorization=Bearer ${openai.api.key}")
    OpenAIResponse getResponse(@RequestBody OpenAIRequest request);
}
