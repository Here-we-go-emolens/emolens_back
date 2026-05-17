package hwan.project2.web.dto.notification;

import hwan.project2.domain.notification.Notification;

public record NotificationResponse(
        Long id,
        String type,
        String title,
        String body,
        String link,
        boolean isRead,
        String createdAt
) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getType().name(),
                n.getTitle(),
                n.getBody(),
                n.getLink(),
                n.isRead(),
                n.getCreatedAt().toString()
        );
    }
}
