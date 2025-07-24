package com.payflow.payflow.repository.ledger;

import com.payflow.payflow.domain.ledger.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long>, LedgerEntryRepositoryCustom {
}
