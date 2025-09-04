package com.payflow.payflow.service.wallet;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.domain.wallet.SettlementCompletedEvent;
import com.payflow.payflow.domain.wallet.Wallet;
import com.payflow.payflow.domain.wallet.WalletTransaction;
import com.payflow.payflow.repository.payment.PaymentOrderRepository;
import com.payflow.payflow.repository.wallet.WalletRepository;
import com.payflow.payflow.repository.wallet.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final WalletRepository walletRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void handleSettlement(PaymentConfirmationSuccessEvent event) {
        if (walletTransactionRepository.existsByOrderId(event.getOrderId())) {
            return;
        }

        List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(event.getOrderId());

        Map<Long, List<PaymentOrder>> ordersBySeller = paymentOrders.stream().collect(Collectors.groupingBy(PaymentOrder::getSellerId));

        Set<Long> sellerIds = ordersBySeller.keySet();

        List<Wallet> wallets = walletRepository.findByUserIdIn(sellerIds);

        List<WalletTransaction> transactions = new ArrayList<>();
        wallets.forEach(wallet -> {
            List<PaymentOrder> sellerOrders = ordersBySeller.getOrDefault(wallet.getUserId(), Collections.emptyList());
            transactions.addAll(wallet.calculateBalanceWith(sellerOrders));
        });

        walletRepository.saveAll(wallets);
        walletTransactionRepository.saveAll(transactions);

        eventPublisher.publishEvent(new SettlementCompletedEvent(event.getOrderId()));
    }
}
