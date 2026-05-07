package hwan.project2.service.ai.dto;

import java.util.List;

public record AnalysisResult(
        List<EmotionData> emotions,
        List<String> keywords,
        String oneLiner,
        List<Recommendation> recommendations
) {
    public record EmotionData(String name, int score) {}
    public record Recommendation(String type, String content) {}
}
