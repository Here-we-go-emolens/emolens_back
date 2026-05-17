package hwan.project2.web.user;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.auth.AuthService;
import hwan.project2.service.notification.NotificationService;
import hwan.project2.web.dto.ChangePasswordRequest;
import hwan.project2.web.dto.UpdateProfileRequest;
import hwan.project2.web.dto.notification.NotificationSettingsResponse;
import hwan.project2.web.dto.notification.NotificationSettingsUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final NotificationService notificationService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest req) {
        authService.updateProfile(principal.getId(), req.name(), req.profileImageUrl());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ChangePasswordRequest req) {
        authService.changePassword(principal.getId(), req.currentPassword(), req.newPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String token = resolveToken(request);
        authService.withdraw(principal.getId(), token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/plan/upgrade")
    public ResponseEntity<Void> upgradePlan(
            @AuthenticationPrincipal UserPrincipal principal) {
        authService.upgradePlan(principal.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification-settings")
    public ResponseEntity<NotificationSettingsResponse> getNotificationSettings(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(notificationService.getSettings(principal.getId()));
    }

    @PatchMapping("/notification-settings")
    public ResponseEntity<Void> updateNotificationSettings(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody NotificationSettingsUpdateRequest req) {
        notificationService.updateSettings(principal.getId(), req);
        return ResponseEntity.ok().build();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return "";
    }
}
