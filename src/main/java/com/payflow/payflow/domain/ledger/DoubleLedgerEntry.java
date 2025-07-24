package com.payflow.payflow.domain.ledger;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoubleLedgerEntry {
    private LedgerEntry credit;
    private LedgerEntry debit;
    private LedgerTransaction transaction;


    @Builder
    public DoubleLedgerEntry(LedgerEntry credit, LedgerEntry debit, LedgerTransaction transaction) {
        if (!credit.getAmount().equals(debit.getAmount())) {
            throw new IllegalArgumentException("A double ledger entry requires that the amounts for both the credit and debit are same.");
        }
        this.credit = credit;
        this.debit = debit;
        this.transaction = transaction;
    }
}
