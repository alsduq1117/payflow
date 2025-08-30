package com.payflow.payflow.controller.cart;

import com.payflow.payflow.service.auth.UserPrincipal;
import com.payflow.payflow.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart-item/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable(name = "productId") Long productId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.addProductToCart(userPrincipal.getUser().getId(), productId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/cart-item/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable(name = "productId") Long productId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.deleteProductFromCart(userPrincipal.getUser().getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
