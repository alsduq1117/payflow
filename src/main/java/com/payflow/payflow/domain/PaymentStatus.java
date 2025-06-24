package com.payflow.payflow.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    NOT_STARTED("결제 승인 시작 전"),
    EXECUTING("결제 승인 중"),
    SUCCESS("결제 승인 성공"),
    FAILURE("결제 승인 실패"),
    UNKNOWN("결제 승인 알 수 없는 상태");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public static PaymentStatus get(String status) {
        for (PaymentStatus s : values()) {
            if (s.name().equals(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("PaymentStatus: " + status + " 는 올바르지 않은 결제 상태입니다.");
    }
}