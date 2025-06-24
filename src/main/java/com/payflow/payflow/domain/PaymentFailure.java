package com.payflow.payflow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PaymentFailure {
    private final String code;
    private final String reason;
}