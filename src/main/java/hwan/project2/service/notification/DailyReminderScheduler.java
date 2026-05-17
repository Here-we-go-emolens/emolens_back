package hwan.project2.service.notification;

import hwan.project2.domain.member.Member;
import hwan.project2.domain.member.MemberStatus;
import hwan.project2.domain.member.repo.MemberRepository;
import hwan.project2.domain.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyReminderScheduler {

    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    // 매일 저녁 8시 실행
    @Scheduled(cron = "0 0 20 * * *")
    @Transactional
    public void sendDailyReminders() {
        List<Member> targets = memberRepository.findByStatusAndNotifyDailyReminderTrue(MemberStatus.ACTIVE);
        log.info("일기 작성 알림 대상: {}명", targets.size());

        for (Member member : targets) {
            notificationService.create(
                    member.getId(),
                    NotificationType.DAILY_REMINDER,
                    "오늘 일기를 작성해보세요",
                    "오늘 하루는 어떠셨나요? 감정을 기록해보세요",
                    "/write"
            );
        }
    }
}
