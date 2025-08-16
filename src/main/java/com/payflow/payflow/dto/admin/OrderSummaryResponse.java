package com.payflow.payflow.dto.admin;

import com.payflow.payflow.domain.payment.PaymentMethod;
import com.payflow.payflow.domain.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class OrderSummaryResponse {
    private Long id;
    private String buyerNickname;
    private Long amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;

    @Builder
    public OrderSummaryResponse(Long id, String buyerNickname, Long amount, PaymentStatus status, PaymentMethod method, LocalDateTime approvedAt, LocalDateTime createdAt) {
        this.id = id;
        this.buyerNickname = buyerNickname;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
    }
}