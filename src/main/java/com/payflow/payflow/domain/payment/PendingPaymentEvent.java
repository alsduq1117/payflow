package com.payflow.payflow.domain.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PendingPaymentEvent {

    private Long paymentEventId;
    private String paymentKey;
    private String orderId;
    private List<PendingPaymentOrder> pendingPaymentOrders;


    @Builder
    public PendingPaymentEvent(Long paymentEventId, String paymentKey, String orderId, List<PendingPaymentOrder> pendingPaymentOrders) {
        this.paymentEventId = paymentEventId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.pendingPaymentOrders = pendingPaymentOrders;
    }

    public Long totalAmount() {
        return pendingPaymentOrders.stream()
                .mapToLong(PendingPaymentOrder::getAmount)
                .sum();
    }

}
