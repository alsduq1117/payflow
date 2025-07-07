package com.payflow.payflow.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payment_orders")
@NoArgsConstructor
public class PaymentOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_event_id", nullable = false)
    private Long paymentEventId;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_order_status", nullable = false)
    private PaymentStatus status;

    @Column(name = "ledger_updated", nullable = false)
    private Boolean isLedgerUpdated;

    @Column(name = "wallet_updated", nullable = false)
    private Boolean isWalletUpdated;


    @Builder
    public PaymentOrder(Long paymentEventId, Long sellerId, Long productId, String orderId, Long amount, PaymentStatus paymentStatus, Boolean isLedgerUpdated, Boolean isWalletUpdated) {
        this.paymentEventId = paymentEventId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status != null ? status : PaymentStatus.NOT_STARTED;
        this.isLedgerUpdated = isLedgerUpdated != null ? isLedgerUpdated : false;
        this.isWalletUpdated = isWalletUpdated != null ? isWalletUpdated : false;
    }
}
