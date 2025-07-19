package com.payflow.payflow.service.ledger;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerService {

    @Transactional
    public void recordDoubleLedgerEntry(PaymentConfirmationSuccessEvent event) {
    }
}
