package com.payflow.payflow.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemResponse {

    private Long cartId;
    private Long productId;
    private String productName;
    private Long price;
    private String thumbnailUrl;


    @Builder
    public CartItemResponse(Long cartId, Long productId, String productName, Long price, String thumbnailUrl) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }
}