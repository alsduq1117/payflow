package com.payflow.payflow.exception.cart;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AlreadyAddedInCartException extends CustomException {

    private static final String MESSAGE = "이미 장바구니에 담긴 제품입니다.";

    public AlreadyAddedInCartException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
