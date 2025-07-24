package com.payflow.payflow.service.ledger;

import com.payflow.payflow.domain.ledger.*;
import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.repository.ledger.LedgerEntryRepository;
import com.payflow.payflow.repository.payment.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final DoubleLedgerEntryProcessor doubleLedgerEntryProcessor;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void recordDoubleLedgerEntry(PaymentConfirmationSuccessEvent event) {

        DoubleAccountsForLedger doubleAccountsForLedger = doubleLedgerEntryProcessor.getDoubleAccountsForLedger(FinanceType.PAYMENT_ORDER);
        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(event.getOrderId());

        List<DoubleLedgerEntry> doubleLedgerEntries = LedgerEntry.createDoubleLedgerEntry(doubleAccountsForLedger, paymentOrders);

        List<LedgerEntry> ledgerEntries = doubleLedgerEntries.stream()
                .flatMap(e -> Stream.of(e.getCredit(), e.getDebit()))
                .collect(Collectors.toList());

        ledgerEntryRepository.saveAll(ledgerEntries);

        eventPublisher.publishEvent(new LedgerEntryCompletedEvent(event.getOrderId()));
    }
}
