package hwan.project2.web.dto.diary;

import hwan.project2.domain.diary.Weather;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaryUpdateRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank String content,
        Weather weather,
        boolean isSecret
) {}
