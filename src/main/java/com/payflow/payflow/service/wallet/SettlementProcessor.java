package com.payflow.payflow.service.wallet;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.domain.wallet.Wallet;
import com.payflow.payflow.domain.wallet.WalletTransaction;
import com.payflow.payflow.repository.payment.PaymentOrderRepository;
import com.payflow.payflow.repository.wallet.WalletRepository;
import com.payflow.payflow.repository.wallet.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SettlementProcessor {

    private final PaymentOrderRepository paymentOrderRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processSettlementWithRecent(PaymentConfirmationSuccessEvent event) {
        List<PaymentOrder> paymentOrders = paymentOrderRepository
                .findByOrderId(event.getOrderId());

        Map<Long, List<PaymentOrder>> ordersBySeller = paymentOrders.stream()
                .collect(Collectors.groupingBy(PaymentOrder::getSellerId));

        Set<Long> sellerIds = ordersBySeller.keySet();

        List<Wallet> wallets = walletRepository.findByUserIdIn(sellerIds);

        List<WalletTransaction> transactions = new ArrayList<>();
        wallets.forEach(wallet -> {
            List<PaymentOrder> sellerOrders = ordersBySeller.getOrDefault(wallet.getUserId(), Collections.emptyList());
            transactions.addAll(wallet.calculateBalanceWith(sellerOrders));
        });

        // 5. 저장
        walletRepository.saveAll(wallets);
        walletTransactionRepository.saveAll(transactions);
    }

}
