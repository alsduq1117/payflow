package com.payflow.payflow.dto.payment;

import com.payflow.payflow.domain.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PendingPaymentFlatRow {
    private Long paymentEventId;
    private String paymentKey;
    private String orderId;
    private Long paymentOrderId;
    private PaymentStatus status;
    private Long amount;
    private Byte failedCount;
    private Byte threshold;

    @Builder
    public PendingPaymentFlatRow(Long paymentEventId, String paymentKey, String orderId, Long paymentOrderId, PaymentStatus status, Long amount, Byte failedCount, Byte threshold) {
        this.paymentEventId = paymentEventId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.paymentOrderId = paymentOrderId;
        this.status = status;
        this.amount = amount;
        this.failedCount = failedCount;
        this.threshold = threshold;
    }
}
