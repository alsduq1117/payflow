package com.payflow.payflow.service.wallet;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import com.payflow.payflow.repository.wallet.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.RetryException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    @Qualifier("lockingRetryTemplate")
    private final RetryTemplate lockingRetryTemplate;
    private final WalletTransactionRepository walletTransactionRepository;
    private final SettlementProcessor settlementProcessor;

    public void handleSettlement(PaymentConfirmationSuccessEvent event) {
        if (walletTransactionRepository.existsByOrderId(event.getOrderId())) {
            return;
        }

        try {
            lockingRetryTemplate.execute(context -> {
                settlementProcessor.processSettlementWithRecent(event);
                return null;
            });
        } catch (RetryException e) {
            log.error("락 재시도 실패: orderId={}", event.getOrderId(), e);
        }
    }
}
