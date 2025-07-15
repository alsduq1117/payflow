package com.payflow.payflow.controller.payment;

import com.payflow.payflow.domain.payment.CheckoutCommand;
import com.payflow.payflow.dto.payment.CheckoutRequest;
import com.payflow.payflow.dto.payment.CheckoutResponse;
import com.payflow.payflow.service.payment.CheckoutService;
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
