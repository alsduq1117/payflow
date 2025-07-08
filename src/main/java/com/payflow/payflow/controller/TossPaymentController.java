package com.payflow.payflow.controller;

import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import com.payflow.payflow.domain.PaymentConfirmationResult;
import com.payflow.payflow.service.PaymentConfirmService;
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
        PaymentConfirmationResult result = paymentConfirmService.confirm(request);
        return ResponseEntity.ok().body(result);
    }
}
