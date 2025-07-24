package com.payflow.payflow.repository.ledger;

import com.payflow.payflow.domain.ledger.Account;
import com.payflow.payflow.domain.ledger.AccountName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByName(String name);
}
