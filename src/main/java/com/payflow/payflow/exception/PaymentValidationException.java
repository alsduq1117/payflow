package com.payflow.payflow.exception;

public class PaymentValidationException extends CustomException {

    public PaymentValidationException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
