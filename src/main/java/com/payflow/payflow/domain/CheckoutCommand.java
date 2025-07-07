package com.payflow.payflow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CheckoutCommand {

    private Long cartId;
    private List<Long> productIds;
    private Long buyerId;
    private String idempotencyKey;

    @Builder
    public CheckoutCommand(Long cartId, List<Long> productIds, Long buyerId, String idempotencyKey) {
        this.cartId = cartId;
        this.productIds = productIds;
        this.buyerId = buyerId;
        this.idempotencyKey = idempotencyKey;
    }
}
