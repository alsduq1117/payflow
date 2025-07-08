package com.payflow.payflow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentStatusUpdateCommand {

    private String paymentKey;
    private String orderId;
    private PaymentStatus status;
    private PaymentExtraDetails extraDetails;
    private PaymentFailure failure;

    @Builder
    public PaymentStatusUpdateCommand(String paymentKey, String orderId, PaymentStatus status, PaymentExtraDetails extraDetails, PaymentFailure failure) {

        if (status != PaymentStatus.SUCCESS && status != PaymentStatus.FAILURE && status != PaymentStatus.UNKNOWN) {
            throw new IllegalArgumentException("결제 상태 (status: " + status + ") 는 올바르지 않은 결제 상태입니다.");
        }

        if (status == PaymentStatus.SUCCESS && extraDetails == null) {
            throw new IllegalArgumentException("PaymentStatus 값이 SUCCESS 라면 PaymentExtraDetails 는 null 이 되면 안됩니다.");
        }

        if (status == PaymentStatus.FAILURE && failure == null) {
            throw new IllegalArgumentException("PaymentStatus 값이 FAILURE 라면 PaymentFailure 는 null 이 되면 안됩니다.");
        }

        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.status = status;
        this.extraDetails = extraDetails;
        this.failure = failure;
    }

    public static PaymentStatusUpdateCommand from(PaymentExecutionResult result) {
        return PaymentStatusUpdateCommand.builder()
                .paymentKey(result.getPaymentKey())
                .orderId(result.getOrderId())
                .status(result.paymentStatus())
                .extraDetails(result.getExtraDetails())
                .failure(result.getFailure())
                .build();
    }
}
