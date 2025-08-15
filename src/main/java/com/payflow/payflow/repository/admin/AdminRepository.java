package com.payflow.payflow.repository.admin;

import com.payflow.payflow.domain.payment.PaymentStatus;
import com.payflow.payflow.domain.payment.QPaymentEvent;
import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.payflow.payflow.dto.admin.DashboardMetricResponse;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class AdminRepository implements AdminMetricsRepository {

    private final JPAQueryFactory queryFactory;

    private static final int APPROVAL_DELAY_THRESHOLD_SEC = 120;
    private static final long HIGH_AMOUNT_THRESHOLD = 100_000L;


    @Override
    public DashboardMetricResponse getDashboardMetrics() {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime oneHourAgo = now.minusHours(1);

        long todaySuccessCount = queryFactory
                .select(po.id.count())
                .from(po)
                .where(
                        po.status.eq(PaymentStatus.SUCCESS)
                                .and(po.createdAt.goe(todayStart))
                )
                .fetchOne();

        long todayFailureCount = queryFactory
                .select(po.id.count())
                .from(po)
                .where(
                        po.status.eq(PaymentStatus.FAILURE)
                                .and(po.createdAt.goe(todayStart))
                )
                .fetchOne();

        Long todaySuccessAmount = queryFactory
                .select(po.amount.sum())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(todayStart)))
                .fetchOne();

        int todayFailureRate = (todaySuccessCount + todayFailureCount) == 0
                ? 0
                : (int) Math.round(100.0 * todayFailureCount / (todaySuccessCount + todayFailureCount));

        int todayApproveRate = 100 - todayFailureRate;

        long lastHourSuccess = queryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(oneHourAgo)))
                .fetchOne();

        long lastHourFailure = queryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.FAILURE)
                        .and(po.createdAt.goe(oneHourAgo)))
                .fetchOne();

        int lastHourApproveRate = (lastHourSuccess + lastHourFailure) == 0
                ? 0
                : (int) Math.round(100.0 * lastHourSuccess / (lastHourSuccess + lastHourFailure));

        long approvalDelayExceededCount = queryFactory
                .select(pe.id.count())
                .from(pe)
                .where(pe.approvedAt.isNotNull()
                        .and(pe.createdAt.goe(todayStart))
                        .and(Expressions.numberTemplate(Integer.class,
                                        "TIMESTAMPDIFF(SECOND, {0}, {1})", pe.createdAt, pe.approvedAt)
                                .gt(APPROVAL_DELAY_THRESHOLD_SEC)))
                .fetchOne();

        long highAmountCount = queryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(todayStart))
                        .and(po.amount.goe(BigDecimal.valueOf(HIGH_AMOUNT_THRESHOLD))))
                .fetchOne();

        Long highAmountSum = queryFactory
                .select(po.amount.sum())   // ← 템플릿 NO, 타입세이프
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(todayStart))
                        .and(po.amount.goe(HIGH_AMOUNT_THRESHOLD)))
                .fetchOne();

        return DashboardMetricResponse.builder()
                .todaySuccessCount(todaySuccessCount)
                .todaySuccessAmount(todaySuccessAmount != null ? todaySuccessAmount : 0L)
                .todayFailureCount(todayFailureCount)
                .todayFailureRate(todayFailureRate)
                .todayApproveRate(todayApproveRate)
                .lastHourApproveRate(lastHourApproveRate)
                .approvalDelayExceededCount(approvalDelayExceededCount)
                .highAmountCount(highAmountCount)
                .highAmountSum(highAmountSum != null ? highAmountSum : 0L)
                .build();
    }
}
