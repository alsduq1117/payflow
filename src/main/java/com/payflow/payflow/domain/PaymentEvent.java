package com.payflow.payflow.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment_events")
@NoArgsConstructor
public class PaymentEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethod paymentMethod;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "is_payment_done", nullable = false)
    private Boolean isPaymentDone;


    @Builder
    public PaymentEvent(Long buyerId, String orderName, String orderId, String paymentKey, PaymentType paymentType, PaymentMethod paymentMethod, LocalDateTime approvedAt, Boolean isPaymentDone) {
        this.buyerId = buyerId;
        this.orderName = orderName;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.approvedAt = approvedAt;
        this.isPaymentDone = isPaymentDone;
    }

}
