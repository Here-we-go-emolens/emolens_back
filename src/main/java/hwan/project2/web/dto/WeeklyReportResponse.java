package hwan.project2.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WeeklyReportResponse(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        String weeklySummary,
        String dominantEmotion,
        String recommendedAction,
        String recommendationMessage,
        String searchKeyword,
        boolean isActionCompleted,
        LocalDateTime createdAt
) {}
