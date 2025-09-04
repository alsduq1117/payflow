package com.payflow.payflow.domain.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementCompletedEvent {

    private String orderId;

    @Builder
    public SettlementCompletedEvent(String orderId) {
        this.orderId = orderId;
    }
}
