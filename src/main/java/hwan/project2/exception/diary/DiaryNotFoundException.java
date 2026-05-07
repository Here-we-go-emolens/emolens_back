package hwan.project2.exception.diary;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException() {
        super("Diary not found");
    }
}
