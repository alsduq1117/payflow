package com.payflow.payflow.domain.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmationSuccessEvent {

    private String orderId;

    @Builder
    public PaymentConfirmationSuccessEvent(String orderId) {
        this.orderId = orderId;
    }
}
