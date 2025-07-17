package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.payflow.payflow.exception.payment.PaymentValidationException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentValidationRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isValid(String orderId, Long amount) {
        Long totalAmount = queryFactory.select(QPaymentOrder.paymentOrder.amount.sum())
                .from(QPaymentOrder.paymentOrder)
                .where(QPaymentOrder.paymentOrder.orderId.eq(orderId))
                .fetchFirst();

        if (!totalAmount.equals(amount)) {
            throw new PaymentValidationException("결제 금액이 일치하지 않습니다. (orderId: " + orderId + ", 요청 금액: " + amount + ", 실제 금액: " + totalAmount + ")");
        }

        return true;

    }
}
