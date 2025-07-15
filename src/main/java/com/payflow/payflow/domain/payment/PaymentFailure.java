package com.payflow.payflow.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFailure {
    private final String errorCode;
    private final String message;
}