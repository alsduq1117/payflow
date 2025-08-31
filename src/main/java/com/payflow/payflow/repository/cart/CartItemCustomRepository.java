package com.payflow.payflow.repository.cart;

import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.dto.cart.CartItemResponse;

import java.util.List;

public interface CartItemCustomRepository {
    List<CartItemResponse> findCartItemsByCartId(Long cartId);

    void removePurchasedItems(String orderId);
}
