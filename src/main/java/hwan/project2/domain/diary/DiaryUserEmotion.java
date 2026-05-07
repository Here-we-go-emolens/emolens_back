package hwan.project2.domain.diary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryUserEmotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String emotionName;
    private int score;

    @Column(name = "emotion_order")
    private int emotionOrder;

    public static DiaryUserEmotion create(Diary diary, String emotionName, int score, int emotionOrder) {
        DiaryUserEmotion e = new DiaryUserEmotion();
        e.diary = diary;
        e.emotionName = emotionName;
        e.score = score;
        e.emotionOrder = emotionOrder;
        return e;
    }
}
