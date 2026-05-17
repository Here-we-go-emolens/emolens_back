package hwan.project2.web.dto.notification;

public record NotificationSettingsUpdateRequest(
        boolean notify_daily_reminder,
        boolean notify_ai_analysis,
        boolean notify_weekly_report,
        boolean notify_friend_activity
) {}
