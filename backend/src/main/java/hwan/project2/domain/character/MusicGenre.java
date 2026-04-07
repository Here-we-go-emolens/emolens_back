package hwan.project2.domain.character;

public enum MusicGenre {
    KPOP("K-POP"),
    INDIE("인디"),
    JAZZ("재즈"),
    CLASSICAL("클래식"),
    HIPHOP("힙합"),
    POP("팝"),
    ANY("장르 무관");

    private final String label;

    MusicGenre(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }
}
