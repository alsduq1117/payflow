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
    private String orderId;
    private String buyerNickname;
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private String fileUrl;
    private Long amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;

    @Builder
    public OrderSummaryResponse(Long id, String orderId, String buyerNickname, Long productId, String productName, String thumbnailUrl, String fileUrl, Long amount, PaymentStatus status, PaymentMethod method, LocalDateTime approvedAt, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.buyerNickname = buyerNickname;
        this.productId = productId;
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
        this.fileUrl = fileUrl;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
    }

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