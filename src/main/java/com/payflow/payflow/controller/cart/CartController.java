package com.payflow.payflow.controller.cart;

import com.payflow.payflow.dto.cart.CartItemResponse;
import com.payflow.payflow.dto.cart.CreateCartItemRequest;
import com.payflow.payflow.dto.cart.DeleteCartItemsRequest;
import com.payflow.payflow.service.auth.UserPrincipal;
import com.payflow.payflow.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Void> addItemToCart(@RequestBody @Valid CreateCartItemRequest createCartItemRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.addItemsToCart(userPrincipal.getUser().getId(), createCartItemRequest.getProductId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponse>> getItemsFromCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok().body(cartService.getItemsFromCart(userPrincipal.getUser().getId()));
    }

    @PostMapping("/items/delete")
    public ResponseEntity<Void> deleteItemsFromCart(@RequestBody @Valid DeleteCartItemsRequest deleteCartItemsRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.deleteItemsFromCart(userPrincipal.getUser().getId(), deleteCartItemsRequest.getProductIds());
        return ResponseEntity.noContent().build();
    }
}
