package com.payflow.payflow.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFailure {
    private final String code;
    private final String reason;
}