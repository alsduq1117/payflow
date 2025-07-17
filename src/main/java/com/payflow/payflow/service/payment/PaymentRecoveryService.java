package com.payflow.payflow.service.payment;

import com.payflow.payflow.client.toss.TossPaymentExecutor;
import com.payflow.payflow.domain.payment.*;
import com.payflow.payflow.repository.payment.PaymentRepository;
import com.payflow.payflow.repository.payment.PaymentStatusUpdateRepository;
import com.payflow.payflow.repository.payment.PaymentValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class PaymentRecoveryService {

    private final PaymentRepository paymentRepository;
    private final PaymentStatusUpdateRepository paymentStatusUpdateRepository;
    private final PaymentValidationRepository paymentValidationRepository;
    private final TossPaymentExecutor tossPaymentExecutor;
    private final PaymentErrorHandler paymentErrorHandler;
    private final Executor recoveryExecutor;

    @Scheduled(fixedDelay = 180000, initialDelay = 180000)
    public void recovery() {
        List<PendingPaymentEvent> pendingPayments = paymentRepository.findPendingPayments();

        for (PendingPaymentEvent paymentEvent : pendingPayments) {
            PaymentConfirmCommand command = PaymentConfirmCommand.builder()
                    .paymentKey(paymentEvent.getPaymentKey())
                    .orderId(paymentEvent.getOrderId())
                    .amount(paymentEvent.totalAmount())
                    .build();

            recoveryExecutor.execute(() -> {
                try {
                    paymentValidationRepository.isValid(command.getOrderId(), command.getAmount());

                    PaymentExecutionResult result = tossPaymentExecutor.execute(command);

                    paymentStatusUpdateRepository.updatePaymentStatus(PaymentStatusUpdateCommand.from(result));

                } catch (Exception e) {
                    paymentErrorHandler.handlePaymentConfirmationError(e, command);
                }
            });
        }

    }

}
