package com.payflow.payflow.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CheckoutRequest {

    private List<Long> productIds;
    private Long buyerId;
    private String seed;


    @Builder
    public CheckoutRequest(List<Long> productIds, Long buyerId, String seed) {
        this.productIds = productIds;
        this.buyerId = buyerId;
        this.seed = seed;
    }
}
