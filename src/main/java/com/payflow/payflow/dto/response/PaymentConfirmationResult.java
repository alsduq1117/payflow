package com.payflow.payflow.dto.response;

import com.payflow.payflow.domain.PaymentFailure;
import com.payflow.payflow.domain.PaymentStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class PaymentConfirmationResult {

    private final PaymentStatus status;
    private final PaymentFailure failure;
    private final String message;

    public PaymentConfirmationResult(@NonNull PaymentStatus status, PaymentFailure failure) {
        if (status == PaymentStatus.FAILURE && failure == null) {
            throw new IllegalArgumentException("결제 상태 FAILURE 일 때 PaymentFailure 는 null 이 될 수 없습니다.");
        }
        this.status = status;
        this.failure = failure;
        this.message = resolveMessage(status);
    }

    private String resolveMessage(PaymentStatus status) {
        return switch (status) {
            case SUCCESS -> "결제 처리에 성공하였습니다.";
            case FAILURE -> "결제 처리에 실패하였습니다.";
            case UNKNOWN -> "결제 처리 중에 알 수 없는 에러가 발생하였습니다.";
            default -> throw new IllegalStateException("현재 결제 상태 (" + status + ") 는 올바르지 않은 상태입니다.");
        };
    }
}