package com.payflow.payflow.exception.payment;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PaymentEventNotFoundException extends CustomException {

    private static final String MESSAGE = "해당 PaymentEvent를 찾을 수 없습니다.";


    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
