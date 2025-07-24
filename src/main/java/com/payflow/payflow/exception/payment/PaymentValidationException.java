package com.payflow.payflow.exception.payment;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PaymentValidationException extends CustomException {

    public PaymentValidationException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
