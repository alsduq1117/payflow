package com.payflow.payflow.controller;

import com.payflow.payflow.domain.CheckoutCommand;
import com.payflow.payflow.dto.request.CheckoutRequest;
import com.payflow.payflow.dto.response.CheckoutResponse;
import com.payflow.payflow.service.CheckoutService;
import com.payflow.payflow.util.IdempotencyCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @PostMapping("")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        CheckoutCommand command = CheckoutCommand.builder()
                .productIds(checkoutRequest.getProductIds())
                .buyerId(checkoutRequest.getBuyerId())
                .idempotencyKey(IdempotencyCreator.create(checkoutRequest))
                .build();

        CheckoutResponse checkoutResponse = checkoutService.checkout(command);

        return ResponseEntity.ok(checkoutResponse);
    }
}
