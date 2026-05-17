package hwan.project2.web.dto.notification;

import hwan.project2.domain.member.Member;

public record NotificationSettingsResponse(
        boolean notify_daily_reminder,
        boolean notify_ai_analysis,
        boolean notify_weekly_report,
        boolean notify_friend_activity
) {
    public static NotificationSettingsResponse from(Member m) {
        return new NotificationSettingsResponse(
                m.isNotifyDailyReminder(),
                m.isNotifyAiAnalysis(),
                m.isNotifyWeeklyReport(),
                m.isNotifyFriendActivity()
        );
    }
}
