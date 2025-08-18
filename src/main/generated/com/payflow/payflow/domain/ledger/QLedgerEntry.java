package com.payflow.payflow.domain.ledger;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLedgerEntry is a Querydsl query type for LedgerEntry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLedgerEntry extends EntityPathBase<LedgerEntry> {

    private static final long serialVersionUID = 2032257137L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLedgerEntry ledgerEntry = new QLedgerEntry("ledgerEntry");

    public final com.payflow.payflow.domain.common.QBaseOnlyCreated _super = new com.payflow.payflow.domain.common.QBaseOnlyCreated(this);

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLedgerTransaction transaction;

    public final EnumPath<LedgerEntryType> type = createEnum("type", LedgerEntryType.class);

    public QLedgerEntry(String variable) {
        this(LedgerEntry.class, forVariable(variable), INITS);
    }

    public QLedgerEntry(Path<? extends LedgerEntry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLedgerEntry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLedgerEntry(PathMetadata metadata, PathInits inits) {
        this(LedgerEntry.class, metadata, inits);
    }

    public QLedgerEntry(Class<? extends LedgerEntry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.transaction = inits.isInitialized("transaction") ? new QLedgerTransaction(forProperty("transaction")) : null;
    }

}

