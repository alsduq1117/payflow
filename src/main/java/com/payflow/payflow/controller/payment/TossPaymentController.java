package com.payflow.payflow.controller.payment;

import com.payflow.payflow.dto.payment.TossPaymentConfirmRequest;
import com.payflow.payflow.domain.payment.PaymentConfirmationResult;
import com.payflow.payflow.service.payment.PaymentConfirmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/toss")
public class TossPaymentController {

    private final PaymentConfirmService paymentConfirmService;

    @PostMapping("/confirm")
    public ResponseEntity<PaymentConfirmationResult> confirm(@Valid @RequestBody TossPaymentConfirmRequest request) {
        PaymentConfirmationResult result = paymentConfirmService.confirm(request.toCommand());
        return ResponseEntity.ok().body(result);
    }
}
