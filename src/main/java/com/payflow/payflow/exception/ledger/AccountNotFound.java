package com.payflow.payflow.exception.ledger;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AccountNotFound extends CustomException {

    private static final String MESSAGE = "해당 Account 를 찾을 수 없습니다";

    public AccountNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
