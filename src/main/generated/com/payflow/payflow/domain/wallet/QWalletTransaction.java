package com.payflow.payflow.domain.wallet;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWalletTransaction is a Querydsl query type for WalletTransaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalletTransaction extends EntityPathBase<WalletTransaction> {

    private static final long serialVersionUID = -563340899L;

    public static final QWalletTransaction walletTransaction = new QWalletTransaction("walletTransaction");

    public final com.payflow.payflow.domain.common.QBaseEntity _super = new com.payflow.payflow.domain.common.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idempotencyKey = createString("idempotencyKey");

    public final StringPath orderId = createString("orderId");

    public final NumberPath<Long> referenceId = createNumber("referenceId", Long.class);

    public final EnumPath<com.payflow.payflow.repository.wallet.ReferenceType> referenceType = createEnum("referenceType", com.payflow.payflow.repository.wallet.ReferenceType.class);

    public final EnumPath<TransactionType> type = createEnum("type", TransactionType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> walletId = createNumber("walletId", Long.class);

    public QWalletTransaction(String variable) {
        super(WalletTransaction.class, forVariable(variable));
    }

    public QWalletTransaction(Path<? extends WalletTransaction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWalletTransaction(PathMetadata metadata) {
        super(WalletTransaction.class, metadata);
    }

}

