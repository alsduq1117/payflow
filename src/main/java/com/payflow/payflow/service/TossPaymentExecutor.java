package com.payflow.payflow.service;

import com.payflow.payflow.client.TossFeignClient;
import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor {

    private final TossFeignClient tossFeignClient;

    @Value("${PSP.toss.secretKey}")
    private String secretKey;

    public String execute(TossPaymentConfirmRequest command) {
        String encodedKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        String authorizationHeader = "Basic " + encodedKey;

        Map<String, Object> request = new HashMap<>();
        request.put("paymentKey", command.getPaymentKey());
        request.put("orderId", command.getOrderId());
        request.put("amount", command.getAmount());

        return tossFeignClient.confirm(
                authorizationHeader,
                request
        );
    }

}
