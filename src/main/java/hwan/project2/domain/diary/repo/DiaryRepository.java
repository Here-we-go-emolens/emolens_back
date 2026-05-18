package hwan.project2.domain.diary.repo;

import hwan.project2.domain.diary.AnalysisStatus;
import hwan.project2.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {
    boolean existsByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate);
    List<Diary> findByStatus(AnalysisStatus status);
}
