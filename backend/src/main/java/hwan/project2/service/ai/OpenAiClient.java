package hwan.project2.service.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import hwan.project2.service.ai.dto.AnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OpenAiClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model.analysis}")
    private String analysisModel;

    public OpenAiClient(@Value("${openai.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public AnalysisResult analyze(String systemPrompt, String userPrompt) {
        Map<String, Object> requestBody = Map.of(
                "model", analysisModel,
                "response_format", Map.of("type", "json_object"),
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                )
        );

        String responseBody = restClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return parseAnalysisResult(responseBody);
    }

    private AnalysisResult parseAnalysisResult(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            // OpenAI 에러 응답 처리 (e.g. rate limit, invalid key)
            if (root.has("error")) {
                String errorMsg = root.path("error").path("message").asText();
                log.error("OpenAI API 에러: {}", errorMsg);
                throw new RuntimeException("OpenAI API 에러: " + errorMsg);
            }

            JsonNode choices = root.path("choices");
            if (choices.isEmpty()) {
                throw new RuntimeException("OpenAI 응답에 choices가 없습니다: " + responseBody);
            }

            String content = choices.get(0).path("message").path("content").asText();
            if (content.isBlank()) {
                throw new RuntimeException("OpenAI 응답 content가 비어있습니다");
            }

            return objectMapper.readValue(content, AnalysisResult.class);
        } catch (JsonProcessingException e) {
            log.error("OpenAI 응답 파싱 실패: {}", responseBody, e);
            throw new RuntimeException("AI 분석 응답 파싱에 실패했습니다.", e);
        }
    }
}
