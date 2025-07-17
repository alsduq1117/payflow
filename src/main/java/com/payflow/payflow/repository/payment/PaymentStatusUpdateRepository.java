package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.*;
import com.payflow.payflow.exception.payment.PaymentAlreadyProcessedException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentStatusUpdateRepository {

    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

    public void updatePaymentStatusToExecuting(String orderId, String paymentKey) {
        List<PaymentOrder> paymentOrders = checkPreviousPaymentOrderStatus(orderId);
        insertPaymentHistory(paymentOrders, PaymentStatus.EXECUTING, "PAYMENT_CONFIRMATION_START");
        updatePaymentOrderStatus(paymentOrders, PaymentStatus.EXECUTING);
        updatePaymentKey(orderId, paymentKey);
    }

    public boolean updatePaymentStatus(PaymentStatusUpdateCommand command) {
        return switch (command.getStatus()) {
            case SUCCESS -> updatePaymentStatusToSuccess(command);
            case FAILURE -> updatePaymentStatusToFailure(command);
            case UNKNOWN -> updatePaymentStatusToUnknown(command);
            default ->
                    throw new IllegalArgumentException("결제 상태 (status: " + command.getStatus() + ") 는 올바르지 않은 결제 상태입니다.");
        };
    }

    private boolean updatePaymentStatusToSuccess(PaymentStatusUpdateCommand command) {
        List<PaymentOrder> paymentOrders = selectPaymentOrders(command.getOrderId());
        insertPaymentHistory(paymentOrders, command.getStatus(), "PAYMENT_CONFIRMATION_DONE");
        updatePaymentOrderStatus(paymentOrders, command.getStatus());
        updatePaymentEventExtraDetails(command);
        return true;
    }

    private void updatePaymentEventExtraDetails(PaymentStatusUpdateCommand command) {
        queryFactory.update(QPaymentEvent.paymentEvent)
                .set(QPaymentEvent.paymentEvent.orderName, command.getExtraDetails().getOrderName())
                .set(QPaymentEvent.paymentEvent.paymentMethod, command.getExtraDetails().getMethod())
                .set(QPaymentEvent.paymentEvent.approvedAt, command.getExtraDetails().getApprovedAt())
                .set(QPaymentEvent.paymentEvent.paymentType, command.getExtraDetails().getType())
                .set(QPaymentEvent.paymentEvent.pspRawData, command.getExtraDetails().getPspRawData())
                .where(QPaymentEvent.paymentEvent.orderId.eq(command.getOrderId()))
                .execute();
    }

    private boolean updatePaymentStatusToFailure(PaymentStatusUpdateCommand command) {
        List<PaymentOrder> paymentOrders = selectPaymentOrders(command.getOrderId());
        insertPaymentHistory(paymentOrders, command.getStatus(), command.getFailure().toString());
        updatePaymentOrderStatus(paymentOrders, command.getStatus());
        return true;
    }

    private boolean updatePaymentStatusToUnknown(PaymentStatusUpdateCommand command) {
        List<PaymentOrder> paymentOrders = selectPaymentOrders(command.getOrderId());
        insertPaymentHistory(paymentOrders, command.getStatus(), command.getFailure().toString());
        updatePaymentOrderStatus(paymentOrders, command.getStatus());
        return true;
    }

    private List<PaymentOrder> selectPaymentOrders(String orderId) {
        return queryFactory.selectFrom(QPaymentOrder.paymentOrder)
                .where(QPaymentOrder.paymentOrder.orderId.eq(orderId))
                .fetch();

    }

    private List<PaymentOrder> checkPreviousPaymentOrderStatus(String orderId) {
        List<PaymentOrder> paymentOrders = queryFactory.selectFrom(QPaymentOrder.paymentOrder)
                .where(QPaymentOrder.paymentOrder.orderId.eq(orderId))
                .fetch();

        for (PaymentOrder paymentOrder : paymentOrders) {
            switch (paymentOrder.getStatus()) {
                case SUCCESS:
                    throw new PaymentAlreadyProcessedException(PaymentStatus.SUCCESS);
                case FAILURE:
                    throw new PaymentAlreadyProcessedException(PaymentStatus.FAILURE);
                case NOT_STARTED:
                case UNKNOWN:
                case EXECUTING:
                    break;
            }
        }
        return paymentOrders;
    }

    private void insertPaymentHistory(List<PaymentOrder> paymentOrders, PaymentStatus paymentStatus, String reason) {
        if (paymentOrders.isEmpty()) {
            return;
        }

        String sql = """
                INSERT INTO payment_order_histories 
                    (payment_order_id, previous_status, new_status, reason) 
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PaymentOrder order = paymentOrders.get(i);
                ps.setLong(1, order.getId());
                ps.setString(2, order.getStatus().name());
                ps.setString(3, paymentStatus.toString());
                ps.setString(4, reason);
            }

            @Override
            public int getBatchSize() {
                return paymentOrders.size();
            }
        });

    }

    private void updatePaymentOrderStatus(List<PaymentOrder> paymentOrders, PaymentStatus newStatus) {
        for (PaymentOrder paymentOrder : paymentOrders) {
            paymentOrder.updateStatus(newStatus);
        }
    }

    private void updatePaymentKey(String orderId, String paymentKey) {
        queryFactory.update(QPaymentEvent.paymentEvent)
                .set(QPaymentEvent.paymentEvent.paymentKey, paymentKey)
                .where(QPaymentEvent.paymentEvent.orderId.eq(orderId))
                .execute();
    }
}