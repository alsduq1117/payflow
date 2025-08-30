package com.payflow.payflow.exception.payment;

import com.payflow.payflow.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateOrderIdException extends CustomException {

    private static final String MESSAGE = "중복된 결제 처리입니다.";

    public DuplicateOrderIdException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
