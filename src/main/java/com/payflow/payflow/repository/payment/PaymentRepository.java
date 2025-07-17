package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.*;
import com.payflow.payflow.dto.payment.PendingPaymentFlatRow;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<PendingPaymentEvent> findPendingPayments() {
        QPaymentEvent pe = QPaymentEvent.paymentEvent;
        QPaymentOrder po = QPaymentOrder.paymentOrder;

        List<Long> paymentEventIds = jpaQueryFactory
                .select(pe.id)
                .from(pe)
                .join(po).on(pe.id.eq(po.paymentEventId))
                .where(
                        po.status.eq(PaymentStatus.UNKNOWN)
                                .or(
                                        po.status.eq(PaymentStatus.EXECUTING)
                                                .and(po.updatedAt.loe(LocalDateTime.now().minusMinutes(3)))
                                ),
                        po.failedCount.lt(po.threshold)
                )
                .distinct()
                .limit(10)
                .fetch();

        List<PendingPaymentFlatRow> paymentFlatRows = jpaQueryFactory.select(Projections.constructor(
                        PendingPaymentFlatRow.class,
                        pe.id,
                        pe.paymentKey,
                        pe.orderId,
                        po.id,
                        po.status,
                        po.amount,
                        po.failedCount,
                        po.threshold
                ))
                .from(pe)
                .join(po).on(pe.id.eq(po.paymentEventId))
                .where(pe.id.in(paymentEventIds))
                .fetch();

        return groupToNested(paymentFlatRows);
    }

    public List<PendingPaymentEvent> groupToNested(List<PendingPaymentFlatRow> paymentFlatRows) {
        Map<Long, List<PendingPaymentFlatRow>> grouped = paymentFlatRows.stream()
                .collect(Collectors.groupingBy(PendingPaymentFlatRow::getPaymentEventId));

        return grouped.entrySet().stream()
                .map(entry -> {
                    List<PendingPaymentOrder> orders = entry.getValue().stream()
                            .map(row -> new PendingPaymentOrder(
                                    row.getPaymentOrderId(),
                                    row.getStatus(),
                                    row.getAmount(),
                                    row.getFailedCount(),
                                    row.getThreshold()
                            ))
                            .collect(Collectors.toList());

                    PendingPaymentFlatRow first = entry.getValue().get(0);
                    return new PendingPaymentEvent(
                            first.getPaymentEventId(),
                            first.getPaymentKey(),
                            first.getOrderId(),
                            orders
                    );
                })
                .collect(Collectors.toList());
    }
}
