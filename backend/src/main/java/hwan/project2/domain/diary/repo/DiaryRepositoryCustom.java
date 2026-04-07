package hwan.project2.domain.diary.repo;

import hwan.project2.domain.diary.Diary;

import java.util.List;
import java.util.Optional;

public interface DiaryRepositoryCustom {

    /** 단건 조회: emotions JOIN FETCH, 소유권 검증 포함 */
    Optional<Diary> findByIdWithEmotions(Long diaryId, Long memberId);

    /** 목록 조회: 메타데이터만 (컬렉션 미포함), 최신순 */
    List<Diary> findByMemberIdOrderByDateDesc(Long memberId, long offset, int limit);

    long countByMemberId(Long memberId);

    /** 분석 미완료 일기 조회 (PENDING/FAILED) - 재시도 스케줄러용 */
    List<Diary> findUnanalyzed(int limit);
}
