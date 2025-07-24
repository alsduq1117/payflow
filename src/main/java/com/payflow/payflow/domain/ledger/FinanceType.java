package com.payflow.payflow.domain.ledger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FinanceType {
    PAYMENT_ORDER("결제 주문");

    private final String value;

    @JsonValue
    public String getValue() { return value; }

    @JsonCreator
    public static FinanceType fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PaymentMethod: " + value));
    }
}
