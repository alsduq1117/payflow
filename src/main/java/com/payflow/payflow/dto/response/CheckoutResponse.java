package com.payflow.payflow.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckoutResponse {

    private String orderId;
    private String orderName;
    private Long amount;


    @Builder
    public CheckoutResponse(String orderId, String orderName, Long amount) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
    }
}
