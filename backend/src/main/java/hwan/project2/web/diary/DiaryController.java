package hwan.project2.web.diary;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.diary.DiaryService;
import hwan.project2.web.dto.diary.DiaryCreateRequest;
import hwan.project2.web.dto.diary.DiaryListItemResponse;
import hwan.project2.web.dto.diary.DiaryResponse;
import hwan.project2.web.dto.diary.DiaryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody DiaryCreateRequest req) {
        Long diaryId = diaryService.createDiary(principal.getId(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryId);
    }

    @GetMapping("/{diaryId}")
    public DiaryResponse get(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long diaryId) {
        return diaryService.getDiary(principal.getId(), diaryId);
    }

    @GetMapping
    public Page<DiaryListItemResponse> list(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return diaryService.getMyDiaries(principal.getId(), PageRequest.of(page, size));
    }

    @PostMapping("/{diaryId}/update")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long diaryId,
            @Valid @RequestBody DiaryUpdateRequest req) {
        diaryService.updateDiary(principal.getId(), diaryId, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{diaryId}/delete")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long diaryId) {
        diaryService.deleteDiary(principal.getId(), diaryId);
        return ResponseEntity.ok().build();
    }
}
