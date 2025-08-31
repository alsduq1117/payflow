package com.payflow.payflow.service.payment;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.service.cart.CartService;
import com.payflow.payflow.service.ledger.LedgerService;
import com.payflow.payflow.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final WalletService walletService;
    private final LedgerService ledgerService;
    private final CartService cartService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = PaymentConfirmationSuccessEvent.class)
    public void handleSettlement(PaymentConfirmationSuccessEvent walletEvent) {
        walletService.handleSettlement(walletEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = PaymentConfirmationSuccessEvent.class)
    public void handleLedger(PaymentConfirmationSuccessEvent ledgerEvent) {
        ledgerService.recordDoubleLedgerEntry(ledgerEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = PaymentConfirmationSuccessEvent.class)
    public void handleCart(PaymentConfirmationSuccessEvent cartEvent) {
        cartService.removePurchasedItems(cartEvent);
    }
}
