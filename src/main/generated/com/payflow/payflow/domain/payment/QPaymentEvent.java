package com.payflow.payflow.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentEvent is a Querydsl query type for PaymentEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentEvent extends EntityPathBase<PaymentEvent> {

    private static final long serialVersionUID = 883165449L;

    public static final QPaymentEvent paymentEvent = new QPaymentEvent("paymentEvent");

    public final com.payflow.payflow.domain.common.QBaseEntity _super = new com.payflow.payflow.domain.common.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> approvedAt = createDateTime("approvedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> buyerId = createNumber("buyerId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPaymentDone = createBoolean("isPaymentDone");

    public final StringPath orderId = createString("orderId");

    public final StringPath orderName = createString("orderName");

    public final StringPath paymentKey = createString("paymentKey");

    public final EnumPath<PaymentMethod> paymentMethod = createEnum("paymentMethod", PaymentMethod.class);

    public final EnumPath<PaymentType> paymentType = createEnum("paymentType", PaymentType.class);

    public final StringPath pspRawData = createString("pspRawData");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPaymentEvent(String variable) {
        super(PaymentEvent.class, forVariable(variable));
    }

    public QPaymentEvent(Path<? extends PaymentEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentEvent(PathMetadata metadata) {
        super(PaymentEvent.class, metadata);
    }

}

