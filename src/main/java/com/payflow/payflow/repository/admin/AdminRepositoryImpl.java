package com.payflow.payflow.repository.admin;

import com.payflow.payflow.domain.auth.QUser;
import com.payflow.payflow.domain.payment.PaymentStatus;
import com.payflow.payflow.domain.payment.QPaymentEvent;
import com.payflow.payflow.domain.payment.QPaymentOrder;
import com.payflow.payflow.dto.admin.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminRepositoryImpl implements AdminMetricsRepository, AdminOrdersRepository, AdminSettlementRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private static final int APPROVAL_DELAY_THRESHOLD_SEC = 120;
    private static final long HIGH_AMOUNT_THRESHOLD = 100_000L;


    @Override
    public DashboardMetricResponse getDashboardMetrics() {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime oneHourAgo = now.minusHours(1);

        long todaySuccessCount = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .where(
                        po.status.eq(PaymentStatus.SUCCESS)
                                .and(po.createdAt.goe(todayStart))
                )
                .fetchOne();

        long todayFailureCount = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .where(
                        po.status.eq(PaymentStatus.FAILURE)
                                .and(po.createdAt.goe(todayStart))
                )
                .fetchOne();

        Long todaySuccessAmount = jpaQueryFactory
                .select(po.amount.sum())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(todayStart)))
                .fetchOne();

        int todayFailureRate = (todaySuccessCount + todayFailureCount) == 0
                ? 0
                : (int) Math.round(100.0 * todayFailureCount / (todaySuccessCount + todayFailureCount));

        int todayApproveRate = 100 - todayFailureRate;

        long lastHourSuccess = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(oneHourAgo)))
                .fetchOne();

        long lastHourFailure = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.FAILURE)
                        .and(po.createdAt.goe(oneHourAgo)))
                .fetchOne();

        int lastHourApproveRate = (lastHourSuccess + lastHourFailure) == 0
                ? 0
                : (int) Math.round(100.0 * lastHourSuccess / (lastHourSuccess + lastHourFailure));

        long approvalDelayExceededCount = jpaQueryFactory
                .select(pe.id.count())
                .from(pe)
                .where(pe.approvedAt.isNotNull()
                        .and(pe.createdAt.goe(todayStart))
                        .and(Expressions.numberTemplate(Integer.class,
                                        "TIMESTAMPDIFF(SECOND, {0}, {1})", pe.createdAt, pe.approvedAt)
                                .gt(APPROVAL_DELAY_THRESHOLD_SEC)))
                .fetchOne();

        long highAmountCount = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(todayStart))
                        .and(po.amount.goe(BigDecimal.valueOf(HIGH_AMOUNT_THRESHOLD))))
                .fetchOne();

        Long highAmountSum = jpaQueryFactory
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


    @Override
    public PageImpl<OrderSummaryResponse> getOrderPage(OrderPageRequest request) {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;
        QUser buyer = QUser.user;

        BooleanBuilder where = new BooleanBuilder();

        if (request.getBuyerNickname() != null && !request.getBuyerNickname().isBlank()) {
            where.and(buyer.nickname.contains(request.getBuyerNickname()));
        }
        if (request.getStatus() != null) {
            where.and(po.status.eq(request.getStatus()));
        }
        if (request.getMethod() != null) {
            where.and(pe.paymentMethod.eq(request.getMethod()));
        }
        if (request.getMinAmount() != null) {
            where.and(po.amount.goe(BigDecimal.valueOf(request.getMinAmount())));
        }
        if (request.getMaxAmount() != null) {
            where.and(po.amount.loe(BigDecimal.valueOf(request.getMaxAmount())));
        }

        long totalCount = jpaQueryFactory
                .select(po.count())
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .leftJoin(buyer).on(buyer.id.eq(pe.buyerId))
                .where(where)
                .fetchOne();


        List<OrderSummaryResponse> result = jpaQueryFactory
                .select(Projections.constructor(
                        OrderSummaryResponse.class,
                        po.id,
                        buyer.nickname,
                        po.amount,
                        po.status,
                        pe.paymentMethod,
                        pe.approvedAt,
                        po.createdAt
                ))
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .leftJoin(buyer).on(buyer.id.eq(pe.buyerId))
                .where(where)
                .offset(request.getOffset())
                .limit(request.getSize())
                .orderBy(po.id.desc())
                .fetch();

        return new PageImpl<>(result, request.getPageable(), totalCount);
    }

    @Override
    public SettlementOverviewResponse getSettlementOverview(LocalDateTime start, LocalDateTime end) {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;

        Long gross = jpaQueryFactory
                .select(po.amount.sum().coalesce(0L))
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(start))
                        .and(po.createdAt.lt(end)))
                .fetchOne();



        Long net = jpaQueryFactory                   //수수료 계산 로직 집어넣어야함
                .select(po.amount.sum().coalesce(0L))
                .from(po)
                .where(po.status.eq(PaymentStatus.SUCCESS)
                        .and(po.createdAt.goe(start))
                        .and(po.createdAt.lt(end)))
                .fetchOne();

        long orderCount = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .where(pe.isPaymentDone.isTrue()
                        .and(po.status.eq(PaymentStatus.SUCCESS))
                        .and(po.createdAt.goe(start))
                        .and(po.createdAt.lt(end)))
                .fetchOne();

        long pending = jpaQueryFactory
                .select(po.id.count())
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .where(pe.isPaymentDone.isFalse()
                        .and(po.status.eq(PaymentStatus.SUCCESS))
                        .and(po.createdAt.goe(start))
                        .and(po.createdAt.lt(end)))
                .fetchOne();

        return SettlementOverviewResponse.builder()
                .gross(gross)
                .net(net)
                .orderCount(orderCount)
                .pending(pending)
                .build();



    }

    @Override
    public Page<SettlementResponse> getSettlement(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        QPaymentOrder po = QPaymentOrder.paymentOrder;
        QPaymentEvent pe = QPaymentEvent.paymentEvent;
        QUser seller = QUser.user;

        List<SettlementResponse> content = jpaQueryFactory
                .select(Projections.constructor(SettlementResponse.class,
                        seller.id,
                        seller.nickname,
                        po.id.countDistinct(),
                        po.amount.sum().coalesce(0L),
                        po.fee.sum().coalesce(0L),
                        po.amount.sum().coalesce(0L),
                        po.updatedAt.max()
                ))
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .leftJoin(seller).on(seller.id.eq(po.sellerId))
                .where(po.createdAt.goe(start)
                        .and(po.createdAt.lt(end))
                        .and(po.status.eq(PaymentStatus.SUCCESS))
                        .and(pe.isPaymentDone.isTrue()))
                .groupBy(seller.id, seller.nickname)
                .orderBy(toOrderSpecifiers(pageable.getSort(), po, pe, seller))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(seller.id.countDistinct())
                .from(po)
                .join(pe).on(pe.id.eq(po.paymentEventId))
                .join(seller).on(seller.id.eq(po.sellerId))
                .where(po.createdAt.goe(start)
                        .and(po.createdAt.lt(end))
                        .and(po.status.eq(PaymentStatus.SUCCESS))
                        .and(pe.isPaymentDone.isTrue()));


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, QPaymentOrder po, QPaymentEvent pe, QUser seller) {
        List<OrderSpecifier<?>> list = new ArrayList<>();
        for (Sort.Order o : sort) {
            var dir = o.isAscending() ? Order.ASC : Order.DESC;
            switch (o.getProperty()) {
                case "sellerId"     -> list.add(new OrderSpecifier<>(dir, seller.id));
                case "nickname"     -> list.add(new OrderSpecifier<>(dir, seller.nickname));
                case "orderCount"   -> list.add(new OrderSpecifier<>(dir, po.id.countDistinct()));
                case "gross"        -> list.add(new OrderSpecifier<>(dir, po.amount.sum()));
                case "fee"          -> list.add(new OrderSpecifier<>(dir, po.fee.sum()));
                case "net"          -> list.add(new OrderSpecifier<>(dir, po.amount.sum().subtract(po.fee.sum())));
                case "lastPayoutAt" -> list.add(new OrderSpecifier<>(dir, pe.approvedAt.max()));
                default             -> list.add(new OrderSpecifier<>(Order.DESC, po.amount.sum().subtract(po.fee.sum())));
            }
        }
        return list.toArray(OrderSpecifier[]::new);
    }
}
