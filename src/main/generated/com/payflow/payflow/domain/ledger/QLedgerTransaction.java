package com.payflow.payflow.domain.ledger;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLedgerTransaction is a Querydsl query type for LedgerTransaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLedgerTransaction extends EntityPathBase<LedgerTransaction> {

    private static final long serialVersionUID = -334932067L;

    public static final QLedgerTransaction ledgerTransaction = new QLedgerTransaction("ledgerTransaction");

    public final com.payflow.payflow.domain.common.QBaseOnlyCreated _super = new com.payflow.payflow.domain.common.QBaseOnlyCreated(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idempotencyKey = createString("idempotencyKey");

    public final StringPath orderId = createString("orderId");

    public final NumberPath<Long> referenceId = createNumber("referenceId", Long.class);

    public final EnumPath<com.payflow.payflow.repository.wallet.ReferenceType> referenceType = createEnum("referenceType", com.payflow.payflow.repository.wallet.ReferenceType.class);

    public QLedgerTransaction(String variable) {
        super(LedgerTransaction.class, forVariable(variable));
    }

    public QLedgerTransaction(Path<? extends LedgerTransaction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLedgerTransaction(PathMetadata metadata) {
        super(LedgerTransaction.class, metadata);
    }

}

