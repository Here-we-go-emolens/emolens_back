package hwan.project2.service.report;

import hwan.project2.domain.diary.WeeklyReport;
import hwan.project2.domain.diary.repo.WeeklyReportRepository;
import hwan.project2.web.dto.WeeklyReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyReportService {

    private final WeeklyReportRepository weeklyReportRepository;

    @Transactional(readOnly = true)
    public List<WeeklyReportResponse> getMyReports(Long memberId) {
        return weeklyReportRepository.findByMemberIdOrderByStartDateDesc(memberId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public WeeklyReportResponse completeAction(Long memberId, Long reportId) {
        WeeklyReport report = weeklyReportRepository.findByIdAndMemberId(reportId, memberId)
                .orElseThrow(() -> new RuntimeException("리포트를 찾을 수 없습니다."));
        report.completeAction();
        return toResponse(report);
    }

    private WeeklyReportResponse toResponse(WeeklyReport r) {
        return new WeeklyReportResponse(
                r.getId(),
                r.getStartDate(),
                r.getEndDate(),
                r.getWeeklySummary(),
                r.getDominantEmotion(),
                r.getRecommendedAction().name(),
                r.getRecommendationMessage(),
                r.getSearchKeyword(),
                r.isActionCompleted(),
                r.getCreatedAt()
        );
    }
}
