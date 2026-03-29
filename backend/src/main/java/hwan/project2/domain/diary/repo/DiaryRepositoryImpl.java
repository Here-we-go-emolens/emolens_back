package hwan.project2.domain.diary.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hwan.project2.domain.diary.Diary;
import hwan.project2.domain.diary.QDiary;
import hwan.project2.domain.diary.QDiaryEmotion;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * emotions 을 JOIN FETCH 로 한 번에 가져와 N+1 제거.
     * keywords 는 @BatchSize(100) 덕분에 별도 1회 쿼리로 일괄 로딩.
     * member_id 조건을 함께 걸어 소유권 검증을 DB 레벨에서 수행.
     */
    @Override
    public Optional<Diary> findByIdWithEmotions(Long diaryId, Long memberId) {
        QDiary diary = QDiary.diary;
        QDiaryEmotion emotion = QDiaryEmotion.diaryEmotion;

        Diary result = queryFactory
                .selectFrom(diary)
                .leftJoin(diary.emotions, emotion).fetchJoin()
                .where(diary.id.eq(diaryId)
                        .and(diary.member.id.eq(memberId)))
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    /**
     * 목록 조회는 메타데이터만 반환하므로 컬렉션 조인 없음 → N+1 미발생.
     */
    @Override
    public List<Diary> findByMemberIdOrderByDateDesc(Long memberId, long offset, int limit) {
        QDiary diary = QDiary.diary;

        return queryFactory
                .selectFrom(diary)
                .where(diary.member.id.eq(memberId))
                .orderBy(diary.diaryDate.desc(), diary.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public long countByMemberId(Long memberId) {
        QDiary diary = QDiary.diary;

        Long count = queryFactory
                .select(diary.count())
                .from(diary)
                .where(diary.member.id.eq(memberId))
                .fetchOne();

        return count != null ? count : 0L;
    }
}
