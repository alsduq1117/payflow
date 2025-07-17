package com.payflow.payflow.domain.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PendingPaymentOrder {

    private Long paymentOrderId;
    private PaymentStatus status;
    private Long amount;
    private Byte failedCount;
    private Byte threshold;

    @Builder
    public PendingPaymentOrder(Long paymentOrderId, PaymentStatus status, Long amount, Byte failedCount, Byte threshold) {
        this.paymentOrderId = paymentOrderId;
        this.status = status;
        this.amount = amount;
        this.failedCount = failedCount;
        this.threshold = threshold;
    }
}
