package hwan.project2.domain.character;

public enum ActivityType {
    OUTDOOR("야외 활동"),
    INDOOR("실내 활동"),
    SOCIAL("사람들과 함께하는 활동"),
    SOLO("혼자 하는 활동"),
    CREATIVE("창작 활동"),
    ANY("활동 무관");

    private final String label;

    ActivityType(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }
}
