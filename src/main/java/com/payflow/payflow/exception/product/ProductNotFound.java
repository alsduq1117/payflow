package com.payflow.payflow.exception.product;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ProductNotFound extends CustomException {

    private final static String MESSAGE = "해당하는 상품을 찾을 수 없습니다.";

    public ProductNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
