package com.payflow.payflow.repository.wallet;


import com.payflow.payflow.domain.wallet.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    boolean existsByOrderId(String orderId);
}
