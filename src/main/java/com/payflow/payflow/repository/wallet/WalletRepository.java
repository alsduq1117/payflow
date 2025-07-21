package com.payflow.payflow.repository.wallet;

import com.payflow.payflow.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUserIdIn(Set<Long> sellerIds);
}
