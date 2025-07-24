package com.payflow.payflow.domain.ledger;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LedgerEntryCompletedEvent {

    private String orderId;

    @Builder
    public LedgerEntryCompletedEvent(String orderId) {
        this.orderId = orderId;
    }
}