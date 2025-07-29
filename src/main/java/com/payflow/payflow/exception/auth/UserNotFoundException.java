package com.payflow.payflow.exception.auth;

import com.payflow.payflow.exception.CustomException;

public class UserNotFoundException extends CustomException {

    private static final String MESSAGE = "해당 유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
