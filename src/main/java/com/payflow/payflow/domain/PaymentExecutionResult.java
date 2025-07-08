package com.payflow.payflow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentExecutionResult {

    private String paymentKey;
    private String orderId;
    private PaymentExtraDetails extraDetails;
    private PaymentFailure failure;
    private Boolean isSuccess;
    private Boolean isFailure;
    private Boolean isUnknown;

    @Builder
    public PaymentExecutionResult(String paymentKey, String orderId, PaymentExtraDetails extraDetails, PaymentFailure failure, Boolean isSuccess, Boolean isFailure, Boolean isUnknown) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.extraDetails = extraDetails;
        this.failure = failure;
        this.isSuccess = isSuccess;
        this.isFailure = isFailure;
        this.isUnknown = isUnknown;
    }

    public PaymentStatus paymentStatus() {
        if (Boolean.TRUE.equals(isSuccess)) return PaymentStatus.SUCCESS;
        if (Boolean.TRUE.equals(isFailure)) return PaymentStatus.FAILURE;
        if (Boolean.TRUE.equals(isUnknown)) return PaymentStatus.UNKNOWN;
        throw new IllegalStateException("올바르지 않은 결제 상태입니다.");
    }

}




