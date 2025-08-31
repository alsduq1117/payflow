package com.payflow.payflow.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCartItemRequest {

    private Long productId;

    @Builder
    public CreateCartItemRequest(Long productId) {
        this.productId = productId;
    }
}
