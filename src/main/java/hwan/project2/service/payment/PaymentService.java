package hwan.project2.service.payment;

import hwan.project2.domain.member.Member;
import hwan.project2.domain.member.repo.MemberRepository;
import hwan.project2.exception.auth.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Value("${toss.secret-key}")
    private String secretKey;

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void confirmAndUpgrade(Long memberId, String paymentKey, String orderId, int amount) {
        callTossConfirmApi(paymentKey, orderId, amount);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        member.upgradeToPremium();
        log.info("Premium upgraded: memberId={}, orderId={}", memberId, orderId);
    }

    private void callTossConfirmApi(String paymentKey, String orderId, int amount) {
        String encoded = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encoded);

        Map<String, Object> body = Map.of(
                "paymentKey", paymentKey,
                "orderId", orderId,
                "amount", amount
        );

        try {
            restTemplate.exchange(
                    TOSS_CONFIRM_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    Map.class
            );
        } catch (HttpClientErrorException e) {
            log.error("Toss payment confirm failed: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getStatusCode());
        }
    }
}
