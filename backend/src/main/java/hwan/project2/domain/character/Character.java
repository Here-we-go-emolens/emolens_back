package hwan.project2.domain.character;

import hwan.project2.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "character_setting")  // 'character'는 MySQL 예약어
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    private String name; // 캐릭터 이름

    @Enumerated(EnumType.STRING)
    private CharacterTone tone; // 말투

    @Enumerated(EnumType.STRING)
    private CharacterPersonality personality; // 성격

    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre; // 선호 음악 장르

    @Enumerated(EnumType.STRING)
    private ActivityType activityType; // 선호 활동 유형

    public static Character create(Member member, String name, CharacterTone tone,
                                   CharacterPersonality personality,
                                   MusicGenre musicGenre, ActivityType activityType) {
        Character c = new Character();
        c.member = member;
        c.name = name;
        c.tone = tone;
        c.personality = personality;
        c.musicGenre = musicGenre != null ? musicGenre : MusicGenre.ANY;
        c.activityType = activityType != null ? activityType : ActivityType.ANY;
        return c;
    }

    public void update(String name, CharacterTone tone, CharacterPersonality personality,
                       MusicGenre musicGenre, ActivityType activityType) {
        this.name = name;
        this.tone = tone;
        this.personality = personality;
        this.musicGenre = musicGenre != null ? musicGenre : MusicGenre.ANY;
        this.activityType = activityType != null ? activityType : ActivityType.ANY;
    }
}
