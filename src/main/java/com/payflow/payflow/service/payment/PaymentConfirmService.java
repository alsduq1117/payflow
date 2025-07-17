package com.payflow.payflow.service.payment;

import com.payflow.payflow.client.toss.TossPaymentExecutor;
import com.payflow.payflow.domain.payment.PaymentConfirmCommand;
import com.payflow.payflow.domain.payment.PaymentConfirmationResult;
import com.payflow.payflow.domain.payment.PaymentExecutionResult;
import com.payflow.payflow.domain.payment.PaymentStatusUpdateCommand;
import com.payflow.payflow.repository.payment.PaymentStatusUpdateRepository;
import com.payflow.payflow.repository.payment.PaymentValidationRepository;
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
    private final PaymentErrorHandler paymentErrorHandler;

    @Transactional
    public PaymentConfirmationResult confirm(PaymentConfirmCommand request) {
        String orderId = request.getOrderId();
        String paymentKey = request.getPaymentKey();

        try {
            // 1. 결제 상태를 EXECUTING 으로 변경
            paymentStatusUpdateRepository.updatePaymentStatusToExecuting(orderId, paymentKey);
            // 2. 결제 금액 및 요청 금액 검증
            paymentValidationRepository.isValid(orderId, request.getAmount());
            // 3. Toss 결제 승인 API 실행
            PaymentExecutionResult paymentExecutionResult = tossPaymentExecutor.execute(request);
            // 4. 결제 결과에 따라 상태 업데이트
            paymentStatusUpdateRepository.updatePaymentStatus(PaymentStatusUpdateCommand.from(paymentExecutionResult));

            return new PaymentConfirmationResult(paymentExecutionResult.paymentStatus(), paymentExecutionResult.getFailure());
        } catch (Exception e) {
            return paymentErrorHandler.handlePaymentConfirmationError(e, request);
        }
    }
}
