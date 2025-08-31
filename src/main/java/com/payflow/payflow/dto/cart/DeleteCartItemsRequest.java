package com.payflow.payflow.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteCartItemsRequest {

    private List<Long> productIds;

    @Builder
    public DeleteCartItemsRequest(List<Long> productIds) {
        this.productIds = productIds;
    }
}
