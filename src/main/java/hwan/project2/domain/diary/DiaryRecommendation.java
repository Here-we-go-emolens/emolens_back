package hwan.project2.domain.diary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryRecommendation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String type;    // "action" | "music"
    private String content;

    public static DiaryRecommendation create(Diary diary, String type, String content) {
        DiaryRecommendation r = new DiaryRecommendation();
        r.diary = diary;
        r.type = type;
        r.content = content;
        return r;
    }
}
