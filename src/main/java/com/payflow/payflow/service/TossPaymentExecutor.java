package com.payflow.payflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.client.TossFeignClient;
import com.payflow.payflow.client.TossPaymentConfirmationResponse;
import com.payflow.payflow.domain.PSPConfirmationStatus;
import com.payflow.payflow.domain.PaymentExtraDetails;
import com.payflow.payflow.domain.PaymentMethod;
import com.payflow.payflow.domain.PaymentType;
import com.payflow.payflow.dto.request.TossPaymentConfirmRequest;
import com.payflow.payflow.domain.PaymentExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor {

    private final ObjectMapper objectMapper;
    private final TossFeignClient tossFeignClient;

    @Value("${PSP.toss.secretKey}")
    private String secretKey;

    public PaymentExecutionResult execute(TossPaymentConfirmRequest command) {
        String encodedKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        String authorizationHeader = "Basic " + encodedKey;

        Map<String, Object> request = new HashMap<>();
        request.put("paymentKey", command.getPaymentKey());
        request.put("orderId", command.getOrderId());
        request.put("amount", command.getAmount());

        TossPaymentConfirmationResponse response = tossFeignClient.confirm(authorizationHeader, request);

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return PaymentExecutionResult.builder()
                .paymentKey(command.getPaymentKey())
                .orderId(command.getOrderId())
                .extraDetails(PaymentExtraDetails.builder()
                        .type(PaymentType.fromName(response.getType()))
                        .method(PaymentMethod.fromValue(response.getMethod()))
                        .approvedAt(LocalDateTime.parse(response.getApprovedAt(), DateTimeFormatter.ISO_DATE_TIME))
                        .pspRawData(jsonString)
                        .orderName(response.getOrderName())
                        .pspConfirmationStatus(PSPConfirmationStatus.of(response.getStatus()))
                        .totalAmount((long) response.getTotalAmount())
                        .build())
                .isSuccess(true)
                .isFailure(false)
                .isUnknown(false)
                .build();
    }


}
