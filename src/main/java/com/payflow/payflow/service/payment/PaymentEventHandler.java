package com.payflow.payflow.service.payment;

import com.payflow.payflow.domain.ledger.LedgerEntryCompletedEvent;
import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.wallet.SettlementCompltedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventHandler {

    private final PaymentCompleteService paymentCompleteService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = SettlementCompltedEvent.class)
    public void onSettlementCompleted(SettlementCompltedEvent event) {
        log.info("Received settlement completed event: {}", event);
        paymentCompleteService.completePayment(event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = LedgerEntryCompletedEvent.class)
    public void onLedgerEntryCompleted(LedgerEntryCompletedEvent event) {
        log.info("Received ledger entry completed event: {}", event);
        paymentCompleteService.completePayment(event);
    }
}
