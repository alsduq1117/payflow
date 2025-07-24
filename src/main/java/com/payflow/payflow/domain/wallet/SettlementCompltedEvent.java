package com.payflow.payflow.domain.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementCompltedEvent {

    private String orderId;

    @Builder
    public SettlementCompltedEvent(String orderId) {
        this.orderId = orderId;
    }
}
