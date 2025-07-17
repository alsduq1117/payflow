package com.payflow.payflow.service.payment;

import com.payflow.payflow.client.PSPConfirmationException;
import com.payflow.payflow.domain.payment.*;
import com.payflow.payflow.dto.payment.TossPaymentConfirmRequest;
import com.payflow.payflow.exception.payment.PaymentAlreadyProcessedException;
import com.payflow.payflow.exception.payment.PaymentValidationException;
import com.payflow.payflow.repository.payment.PaymentStatusUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentErrorHandler {

    private final PaymentStatusUpdateRepository paymentStatusUpdateRepository;

    public PaymentConfirmationResult handlePaymentConfirmationError(Exception e, PaymentConfirmCommand request) {
        log.error("결제 확인 중 예외 발생: {}", e.getMessage(), e);

        PaymentStatus status;
        PaymentFailure failure;

        if (e instanceof PSPConfirmationException ex) {
            status = ex.getPaymentStatus();
            failure = new PaymentFailure(ex.getErrorCode(), ex.getErrorMessage());
        } else if (e instanceof PaymentValidationException ex) {
            status = PaymentStatus.FAILURE;
            failure = new PaymentFailure("ValidationError", ex.getMessage());
        } else if (e instanceof PaymentAlreadyProcessedException ex) {
            // 중복 예외는 상태 업데이트 없이 바로 응답 반환
            return new PaymentConfirmationResult(ex.getPaymentStatus(), new PaymentFailure("AlreadyProcessed", ex.getMessage()));
        } else {
            status = PaymentStatus.UNKNOWN;
            failure = new PaymentFailure("UnexpectedError", e.getMessage());
        }

        PaymentStatusUpdateCommand failCommand = PaymentStatusUpdateCommand.builder()
                .paymentKey(request.getPaymentKey())
                .orderId(request.getOrderId())
                .status(status)
                .failure(failure)
                .build();

        // 실패 상태 업데이트
        paymentStatusUpdateRepository.updatePaymentStatus(failCommand);

        return new PaymentConfirmationResult(status, failure);
    }
}
