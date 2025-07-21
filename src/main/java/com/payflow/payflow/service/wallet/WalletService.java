package com.payflow.payflow.service.wallet;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.domain.wallet.Wallet;
import com.payflow.payflow.domain.wallet.WalletTransaction;
import com.payflow.payflow.repository.payment.PaymentOrderRepository;
import com.payflow.payflow.repository.wallet.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public void processSettlement(PaymentConfirmationSuccessEvent walletEvent) {

        if(walletTransactionRepository.existsByOrderId((walletEvent.getOrderId()))){
            return;
        }

        List<PaymentOrder> paymentOrders = paymentOrderRepository.getPaymentOrdersByOrderId(walletEvent.getOrderId());
        Map<Long, List<PaymentOrder>> paymentOrdersBySellerId = paymentOrders.stream().collect(Collectors.groupingBy(PaymentOrder::getSellerId));

        List<WalletTransaction> walletTransactions = new ArrayList<>();
        List<Wallet> updatedWallets = getUpdatedWallets(paymentOrdersBySellerId, walletTransactions);
        walletRepository.saveAll(updatedWallets);
        walletTransactionRepository.saveAll(walletTransactions);
    }

    private List<Wallet> getUpdatedWallets(Map<Long, List<PaymentOrder>> paymentOrdersBySellerId, List<WalletTransaction> walletTransactions) {
        Set<Long> sellerIds = paymentOrdersBySellerId.keySet();
        List<Wallet> wallets = walletRepository.findByUserIdIn(sellerIds);

        wallets.forEach(wallet -> {
            List<PaymentOrder> paymentOrders = paymentOrdersBySellerId.get(wallet.getUserId());
            List<WalletTransaction> generatedTransactions = wallet.calculateBalanceWith(paymentOrders);
            walletTransactions.addAll(generatedTransactions);
        });

        return wallets;
    }
}
