package com.payflow.payflow.domain;

import lombok.Getter;

@Getter
public class PaymentConfirmationResult {
    private final PaymentStatus status;
    private final PaymentFailure failure;
    private final String message;

    public PaymentConfirmationResult(PaymentStatus status, PaymentFailure failure) {
        if(status == PaymentStatus.FAILURE && failure == null) {
            throw new IllegalArgumentException("결제 상태가 FAILURE일 때, 실패 사유는 null일 수 없습니다.");
        }
        this.status = status;
        this.failure = failure;
        this.message = resolveMessage(status);

    }

    private String resolveMessage(PaymentStatus status) {
        switch (status) {
            case SUCCESS:
                return "결제 처리에 성공하였습니다.";
            case FAILURE:
                return "결제 처리에 실패하였습니다.";
            case UNKNOWN:
                return "결제 처리 중에 알 수 없는 에러가 발생하였습니다.";
            default:
                throw new IllegalStateException(
                        "현재 결제 상태 (status: " + status + ") 는 올바르지 않은 상태입니다."
                );
        }
    }
}
