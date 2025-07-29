package com.payflow.payflow.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CustomException {


    public UnAuthorizedException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
