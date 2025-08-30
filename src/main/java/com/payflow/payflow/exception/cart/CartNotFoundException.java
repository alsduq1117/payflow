package com.payflow.payflow.exception.cart;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CustomException {

    private static final String MESSAGE = "장바구니를 찾을 수 없습니다.";

    public CartNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
