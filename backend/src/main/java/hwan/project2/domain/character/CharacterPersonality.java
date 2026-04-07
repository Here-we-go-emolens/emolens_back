package hwan.project2.domain.character;

public enum CharacterPersonality {
    EMPATHETIC("공감형", "감정을 충분히 받아주고 위로에 집중하는"),
    POSITIVE("긍정형", "밝고 희망적인 시각으로 응원하는"),
    DIRECT("직설형", "솔직하고 핵심을 짚어주는"),
    PHILOSOPHICAL("철학형", "깊이 있는 통찰과 질문을 던지는");

    private final String label;
    private final String description;

    CharacterPersonality(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() { return label; }
    public String getDescription() { return description; }
}
