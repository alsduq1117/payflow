package com.payflow.payflow.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentOrder is a Querydsl query type for PaymentOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentOrder extends EntityPathBase<PaymentOrder> {

    private static final long serialVersionUID = 892280253L;

    public static final QPaymentOrder paymentOrder = new QPaymentOrder("paymentOrder");

    public final com.payflow.payflow.domain.common.QBaseEntity _super = new com.payflow.payflow.domain.common.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Byte> failedCount = createNumber("failedCount", Byte.class);

    public final NumberPath<Long> fee = createNumber("fee", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isLedgerUpdated = createBoolean("isLedgerUpdated");

    public final BooleanPath isWalletUpdated = createBoolean("isWalletUpdated");

    public final StringPath orderId = createString("orderId");

    public final NumberPath<Long> paymentEventId = createNumber("paymentEventId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public final EnumPath<PaymentStatus> status = createEnum("status", PaymentStatus.class);

    public final NumberPath<Byte> threshold = createNumber("threshold", Byte.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPaymentOrder(String variable) {
        super(PaymentOrder.class, forVariable(variable));
    }

    public QPaymentOrder(Path<? extends PaymentOrder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentOrder(PathMetadata metadata) {
        super(PaymentOrder.class, metadata);
    }

}

