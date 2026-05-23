package hwan.project2.web.payment;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.payment.PaymentService;
import hwan.project2.web.dto.payment.PaymentConfirmRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PaymentConfirmRequest req) {
        paymentService.confirmAndUpgrade(principal.getId(), req.paymentKey(), req.orderId(), req.amount());
        return ResponseEntity.ok().build();
    }
}
