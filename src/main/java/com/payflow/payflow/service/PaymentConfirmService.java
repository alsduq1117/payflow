package com.payflow.payflow.service;

import com.payflow.payflow.domain.PaymentStatusUpdateCommand;
import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import com.payflow.payflow.domain.PaymentConfirmationResult;
import com.payflow.payflow.domain.PaymentExecutionResult;
import com.payflow.payflow.repository.PaymentStatusUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmService {

    private final PaymentStatusUpdateRepository paymentStatusUpdateRepository;
    private final PaymentValidationRepository paymentValidationRepository;
    private final TossPaymentExecutor tossPaymentExecutor;

    @Transactional
    public PaymentConfirmationResult confirm(TossPaymentConfirmRequest request) {
        String orderId = request.getOrderId();
        String paymentKey = request.getPaymentKey();
        // 1. 결제 상태를 EXECUTING 으로 변경
        paymentStatusUpdateRepository.updatePaymentStatusToExecuting(orderId, paymentKey);
        // 2. 결제 금액 및 요청 금액 검증
        paymentValidationRepository.isValid(orderId, request.getAmount());
        // 3. Toss 결제 승인 API 실행
        PaymentExecutionResult paymentExecutionResult = tossPaymentExecutor.execute(request);
        // 4. 결제 결과에 따라 상태 업데이트
        paymentStatusUpdateRepository.updatePaymentStatus(PaymentStatusUpdateCommand.from(paymentExecutionResult));

        return new PaymentConfirmationResult(paymentExecutionResult.paymentStatus(), paymentExecutionResult.getFailure());
    }
}
