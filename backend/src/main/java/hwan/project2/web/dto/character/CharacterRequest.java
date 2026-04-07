package hwan.project2.web.dto.character;

import hwan.project2.domain.character.ActivityType;
import hwan.project2.domain.character.CharacterPersonality;
import hwan.project2.domain.character.CharacterTone;
import hwan.project2.domain.character.MusicGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CharacterRequest(
        @NotBlank @Size(max = 20) String name,
        @NotNull CharacterTone tone,
        @NotNull CharacterPersonality personality,
        MusicGenre musicGenre,     // optional, 기본값 ANY
        ActivityType activityType  // optional, 기본값 ANY
) {}
