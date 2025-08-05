package com.payflow.payflow.controller.payment;

import com.payflow.payflow.service.auth.UserPrincipal;
import com.payflow.payflow.service.payment.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasPurchasedProduct(@RequestParam(name = "productId") Long productId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        return ResponseEntity.ok(purchaseService.hasPurchasedProduct(productId, userId));
    }
}
