package com.payflow.payflow.service.payment;

import com.payflow.payflow.domain.ledger.LedgerEntryCompletedEvent;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.domain.wallet.SettlementCompltedEvent;
import com.payflow.payflow.repository.payment.PaymentEventRepository;
import com.payflow.payflow.repository.payment.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCompleteService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentEventRepository paymentEventRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completePayment(SettlementCompltedEvent event) {
        paymentOrderRepository.markWalletUpdated(event.getOrderId());

        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(event.getOrderId());
        if (isAllUpdated(paymentOrders)) {
            paymentEventRepository.markPaymentDone(event.getOrderId());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completePayment(LedgerEntryCompletedEvent event) {
        paymentOrderRepository.markLedgerUpdated(event.getOrderId());

        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(event.getOrderId());

        if (isAllUpdated(paymentOrders)) {
            paymentEventRepository.markPaymentDone(event.getOrderId());
        }
    }


    private boolean isAllUpdated(List<PaymentOrder> paymentOrders) {
        return paymentOrders.stream().allMatch(o -> o.getIsLedgerUpdated() && o.getIsWalletUpdated());
    }
}
