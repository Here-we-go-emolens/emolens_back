package hwan.project2.domain.character;

public enum CharacterTone {
    FRIENDLY_INFORMAL("다정한 반말"),
    WARM_FORMAL("따뜻한 존댓말"),
    PLAYFUL("발랄한 반말"),
    COOL("냉철한 존댓말");

    private final String description;

    CharacterTone(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
