package com.payflow.payflow.service.payment;

import com.payflow.payflow.client.toss.TossPaymentExecutor;
import com.payflow.payflow.domain.payment.*;
import com.payflow.payflow.repository.payment.PaymentRepository;
import com.payflow.payflow.repository.payment.PaymentStatusUpdateRepository;
import com.payflow.payflow.repository.payment.PaymentValidationRepository;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
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
    private final ThreadPoolBulkheadRegistry bulkheadRegistry;

    @Scheduled(fixedDelay = 3 * 60 * 1000, initialDelay = 3 * 60 * 1000)
    public void recovery() {
        List<PendingPaymentEvent> pendingPayments = paymentRepository.findPendingPayments();
        ThreadPoolBulkhead bulkhead = bulkheadRegistry.bulkhead("paymentRecovery");

        for (PendingPaymentEvent paymentEvent : pendingPayments) {
            PaymentConfirmCommand command = PaymentConfirmCommand.builder()
                    .paymentKey(paymentEvent.getPaymentKey())
                    .orderId(paymentEvent.getOrderId())
                    .amount(paymentEvent.totalAmount())
                    .build();

            bulkhead.executeRunnable(() -> {
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
