package com.payflow.payflow.service;

import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import com.payflow.payflow.dto.response.PaymentConfirmationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmService {

    private final TossPaymentExecutor tossPaymentExecutor;

    public String confirm(TossPaymentConfirmRequest request) {
        log.info("PaymentConfirmService 수행");
        return tossPaymentExecutor.execute(request);
    }
}
