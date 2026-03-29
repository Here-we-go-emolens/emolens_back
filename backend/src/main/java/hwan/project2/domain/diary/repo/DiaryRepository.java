package hwan.project2.domain.diary.repo;

import hwan.project2.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {
}
