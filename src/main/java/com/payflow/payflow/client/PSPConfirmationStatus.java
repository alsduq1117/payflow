package com.payflow.payflow.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum PSPConfirmationStatus {

    DONE("DONE", "완료"),
    CANCELED("CANCELED", "승인된 결제가 취소된 상태"),
    EXPIRED("EXPIRED", "결제 유효 시간이 지나서 만료된 상태"),
    PARTIAL_CANCELED("PARTIAL_CANCELED", "승인된 결제가 부분 취소된 상태"),
    ABORTED("ABORTED", "결제 승인이 실패된 상태"),
    WAITING_FOR_DEPOSIT("WAITING_FOR_DEPOSIT", "가상계좌 입금 대기"),
    IN_PROGRESS("IN_PROGRESS", "결제 인증 완료 상태"),
    READY("READY", "결제 초기 생성 상태");

    private final String value;
    private final String description;

    PSPConfirmationStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static PSPConfirmationStatus of(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("올바르지 않은 PSP 상태 값입니다: " + value));
    }
}