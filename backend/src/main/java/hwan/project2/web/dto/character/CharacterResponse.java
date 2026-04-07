package hwan.project2.web.dto.character;

import hwan.project2.domain.character.Character;

public record CharacterResponse(
        String name,
        String tone,
        String toneDescription,
        String personality,
        String personalityDescription,
        String musicGenre,
        String activityType
) {
    public static CharacterResponse from(Character character) {
        return new CharacterResponse(
                character.getName(),
                character.getTone().name(),
                character.getTone().getDescription(),
                character.getPersonality().name(),
                character.getPersonality().getLabel() + " - " + character.getPersonality().getDescription(),
                character.getMusicGenre().name(),
                character.getActivityType().name()
        );
    }
}
