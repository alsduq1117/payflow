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
        // 1. 결제 주문 조회 (재시도마다 최신 데이터)
        List<PaymentOrder> paymentOrders = paymentOrderRepository
                .getPaymentOrdersByOrderId(event.getOrderId());

        Map<Long, List<PaymentOrder>> ordersBySeller = paymentOrders.stream()
                .collect(Collectors.groupingBy(PaymentOrder::getSellerId));

        // 2. 판매자 ID 추출
        Set<Long> sellerIds = ordersBySeller.keySet();

        // 3. 최신 지갑 데이터 조회 (재시도마다 새로 조회)
        List<Wallet> wallets = walletRepository.findByUserIdIn(sellerIds);

        // 4. 지갑 업데이트 및 트랜잭션 생성
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
