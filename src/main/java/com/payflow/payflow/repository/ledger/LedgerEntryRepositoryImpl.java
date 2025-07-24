package com.payflow.payflow.repository.ledger;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LedgerEntryRepositoryImpl implements LedgerEntryRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
}
