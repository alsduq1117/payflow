package com.payflow.payflow.domain.wallet;

import com.payflow.payflow.domain.common.BaseEntity;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.repository.wallet.ReferenceType;
import com.payflow.payflow.util.IdempotencyCreator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "wallet_transactions")
public class WalletTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id")
    private Long walletId;

    @Column
    private Long amount;

    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "reference_type")
    @Enumerated(value = EnumType.STRING)
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Builder
    public WalletTransaction(Long walletId, Long amount, TransactionType type, String orderId, ReferenceType referenceType, Long referenceId, String idempotencyKey) {
        this.walletId = walletId;
        this.amount = amount;
        this.type = type;
        this.orderId = orderId;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.idempotencyKey = idempotencyKey;
    }

    public static WalletTransaction from(PaymentOrder paymentOrder, Long walletId) {
        return WalletTransaction.builder()
                .walletId(walletId)
                .amount(paymentOrder.getAmount())
                .type(TransactionType.CREDIT)
                .referenceId(paymentOrder.getId())
                .referenceType(ReferenceType.PAYMENT_ORDER)
                .orderId(paymentOrder.getOrderId())
                .idempotencyKey(IdempotencyCreator.create(paymentOrder)) // 수정 필요
                .build();
    }

    public static List<WalletTransaction> from(List<PaymentOrder> orders, Long walletId) {
        return orders.stream()
                .map(order -> from(order, walletId))
                .collect(Collectors.toList());
    }
}