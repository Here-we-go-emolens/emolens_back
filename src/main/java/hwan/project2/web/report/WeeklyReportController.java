package hwan.project2.web.report;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.report.WeeklyReportService;
import hwan.project2.web.dto.WeeklyReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-reports")
@RequiredArgsConstructor
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    @GetMapping
    public List<WeeklyReportResponse> getMyReports(@AuthenticationPrincipal UserPrincipal principal) {
        return weeklyReportService.getMyReports(principal.getId());
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<WeeklyReportResponse> completeAction(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id) {
        return ResponseEntity.ok(weeklyReportService.completeAction(principal.getId(), id));
    }
}
