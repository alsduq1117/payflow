package com.payflow.payflow.exception;

import com.payflow.payflow.domain.PaymentStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PaymentAlreadyProcessedException extends CustomException {

    private static final String SUCCESS_MESSAGE = "이미 처리 성공한 결제입니다.";
    private static final String FAILURE_MESSAGE = "이미 처리 실패한 결제입니다.";
    private static final String DEFAULT_MESSAGE = "이미 처리된 결제입니다.";

    private final PaymentStatus paymentStatus;

    public PaymentAlreadyProcessedException(PaymentStatus paymentStatus) {
        super(getMessageFor(paymentStatus));
        this.paymentStatus = paymentStatus;
    }

    private static String getMessageFor(PaymentStatus status) {
        if (status == PaymentStatus.SUCCESS) {
            return SUCCESS_MESSAGE;
        } else if (status == PaymentStatus.FAILURE) {
            return FAILURE_MESSAGE;
        } else {
            return DEFAULT_MESSAGE;
        }
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
