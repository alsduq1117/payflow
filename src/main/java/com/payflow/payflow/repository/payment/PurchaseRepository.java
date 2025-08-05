package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentStatus;
import com.payflow.payflow.domain.payment.QPaymentEvent;
import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseRepository {

    private final JPAQueryFactory queryFactory;

    public boolean hasPurchasedProduct(Long productId, Long userId) {
        QPaymentOrder paymentOrder = QPaymentOrder.paymentOrder;
        QPaymentEvent paymentEvent = QPaymentEvent.paymentEvent;

        return queryFactory.selectOne()
                .from(paymentOrder)
                .leftJoin(paymentEvent).on(paymentOrder.paymentEventId.eq(paymentEvent.id))
                .where(paymentOrder.productId.eq(productId),
                        paymentEvent.buyerId.eq(userId),
                        paymentOrder.status.eq(PaymentStatus.SUCCESS)
                )
                .fetchFirst() != null;


    }
}
