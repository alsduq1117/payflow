package com.payflow.payflow.exception.ledger;

import com.payflow.payflow.domain.ledger.FinanceType;
import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UnsupportedFinnceTypeException extends CustomException {

    private static final String MESSAGE = "지원하지 않는 finanaceType 입니다";

    public UnsupportedFinnceTypeException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }


}
