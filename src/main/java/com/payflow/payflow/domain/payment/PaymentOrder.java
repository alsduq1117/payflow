package com.payflow.payflow.domain.payment;

import com.payflow.payflow.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "payment_orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(name = "payment_order_status")
    private PaymentStatus status;

    @Column(name = "failed_count")
    private Byte failedCount = 0;

    @Column(name = "threshold")
    private Byte threshold = 5;

    @Column(name = "ledger_updated", nullable = false)
    private Boolean isLedgerUpdated;

    @Column(name = "wallet_updated", nullable = false)
    private Boolean isWalletUpdated;


    @Builder
    public PaymentOrder(Long paymentEventId, Long sellerId, Long productId, String orderId, Long amount, PaymentStatus status, Boolean isLedgerUpdated, Boolean isWalletUpdated, Byte failedCount, Byte threshold) {
        this.paymentEventId = paymentEventId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status != null ? status : PaymentStatus.NOT_STARTED;
        this.isLedgerUpdated = isLedgerUpdated != null ? isLedgerUpdated : false;
        this.isWalletUpdated = isWalletUpdated != null ? isWalletUpdated : false;
        this.failedCount = failedCount != null ? failedCount : 0;
        this.threshold = threshold != null ? threshold : 5;
    }

    public void updateStatus(PaymentStatus newStatus) {
        this.status = newStatus;
    }

    public void confirmWalletUpdate() {
        this.isWalletUpdated = true;
    }

    public void confirmLedgerUpdate() {
        this.isLedgerUpdated = true;
    }
}
