package com.payflow.payflow.service;

import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmService {

    private final TossPaymentExecutor tossPaymentExecutor;

    public String confirm(TossPaymentConfirmRequest request) {
        return tossPaymentExecutor.execute(request);
    }
}
