package com.payflow.payflow.service.wallet;


import com.payflow.payflow.domain.wallet.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    boolean existsByOrderId(String orderId);
}
