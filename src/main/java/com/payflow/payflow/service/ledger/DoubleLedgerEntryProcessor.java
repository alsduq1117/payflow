package com.payflow.payflow.service.ledger;

import com.payflow.payflow.domain.ledger.Account;
import com.payflow.payflow.domain.ledger.AccountName;
import com.payflow.payflow.domain.ledger.DoubleAccountsForLedger;
import com.payflow.payflow.domain.ledger.FinanceType;
import com.payflow.payflow.exception.ledger.AccountNotFound;
import com.payflow.payflow.exception.ledger.UnsupportedFinnceTypeException;
import com.payflow.payflow.repository.ledger.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoubleLedgerEntryProcessor {

    private final AccountRepository accountRepository;

    public DoubleAccountsForLedger getDoubleAccountsForLedger(FinanceType financeType) {
        switch (financeType) {
            case PAYMENT_ORDER:
                Account to = accountRepository.findAccountByName(AccountName.REVENUE.name()).orElseThrow(AccountNotFound::new);
                Account from = accountRepository.findAccountByName(AccountName.ITEM_BUYER.name()).orElseThrow(AccountNotFound::new);
                return new DoubleAccountsForLedger(to, from);

            default:
                throw new UnsupportedFinnceTypeException();
        }

    }
}
