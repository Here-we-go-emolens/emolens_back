package hwan.project2.web.dto.diary;

import hwan.project2.domain.diary.TemplateType;
import hwan.project2.domain.diary.Weather;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DiaryUpdateRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(min = 10, message = "일기 내용은 최소 10자 이상 입력해주세요.") String content,
        Weather weather,
        TemplateType templateType,
        boolean isSecret,
        List<String> imageUrls
) {}
