package com.payflow.payflow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentExtraDetails {
    private PaymentType type;
    private PaymentMethod method;
    private LocalDateTime approvedAt;
    private String orderName;
    private PSPConfirmationStatus pspConfirmationStatus;
    private Long totalAmount;
    private String pspRawData;


    @Builder
    public PaymentExtraDetails(PaymentType type, PaymentMethod method, LocalDateTime approvedAt, String orderName, PSPConfirmationStatus pspConfirmationStatus, Long totalAmount, String pspRawData) {
        this.type = type;
        this.method = method;
        this.approvedAt = approvedAt;
        this.orderName = orderName;
        this.pspConfirmationStatus = pspConfirmationStatus;
        this.totalAmount = totalAmount;
        this.pspRawData = pspRawData;
    }
}