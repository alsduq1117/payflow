package com.payflow.payflow.domain.ledger;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoubleAccountsForLedger {
    private Account to;
    private Account from;

    @Builder
    public DoubleAccountsForLedger(Account to, Account from) {
        this.to = to;
        this.from = from;
    }
}
