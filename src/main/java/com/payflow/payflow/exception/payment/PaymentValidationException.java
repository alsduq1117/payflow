package com.payflow.payflow.exception.payment;

import com.payflow.payflow.exception.CustomException;

public class PaymentValidationException extends CustomException {

    public PaymentValidationException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
