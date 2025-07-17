package com.payflow.payflow.domain.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmCommand {

    private String paymentKey;
    private String orderId;
    private Long amount;

    @Builder
    public PaymentConfirmCommand(String paymentKey, String orderId, Long amount) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }
}
