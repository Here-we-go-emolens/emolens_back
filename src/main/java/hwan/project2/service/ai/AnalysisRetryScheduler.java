package hwan.project2.service.ai;

import hwan.project2.domain.diary.Diary;
import hwan.project2.domain.diary.repo.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PENDING/FAILED 상태 일기를 주기적으로 재분석.
 * 큐 초과, 서버 재시작, OpenAI 일시 오류 등으로 분석이 누락된 경우를 복구.
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class AnalysisRetryScheduler {

    private final DiaryRepository diaryRepository;
    private final DiaryAnalysisService diaryAnalysisService;

    private static final int BATCH_SIZE = 20;

    @Scheduled(fixedDelay = 5 * 60 * 1000) // 5분마다 실행
    public void retryUnanalyzed() {
        List<Diary> unanalyzed = diaryRepository.findUnanalyzed(BATCH_SIZE);
        if (unanalyzed.isEmpty()) return;

        log.info("분석 재시도 대상: {}건", unanalyzed.size());
        for (Diary diary : unanalyzed) {
            diaryAnalysisService.retryAnalyze(diary.getId());
        }
    }
}
