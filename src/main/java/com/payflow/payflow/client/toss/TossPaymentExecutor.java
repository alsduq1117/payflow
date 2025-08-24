package com.payflow.payflow.client.toss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.client.PSPConfirmationStatus;
import com.payflow.payflow.domain.payment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor {

    @Qualifier("pspRetryTemplate")
    private final RetryTemplate pspRetryTemplate;
    private final ObjectMapper objectMapper;
    private final TossFeignClient tossFeignClient;
    

    @Value("${PSP.toss.secretKey}")
    private String secretKey;

    public PaymentExecutionResult execute(PaymentConfirmCommand command) {
        String encodedKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        String authorizationHeader = "Basic " + encodedKey;
        String jsonString;

        Map<String, Object> request = new HashMap<>();
        request.put("paymentKey", command.getPaymentKey());
        request.put("orderId", command.getOrderId());
        request.put("amount", command.getAmount());

        TossPaymentConfirmationResponse response = pspRetryTemplate.execute(context ->
                tossFeignClient.confirm(authorizationHeader, request)
        );

        try {
            jsonString = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("PSP 응답 직렬화 실패", e);
        }

        return PaymentExecutionResult.builder()
                .paymentKey(command.getPaymentKey())
                .orderId(command.getOrderId())
                .extraDetails(PaymentExtraDetails.builder()
                        .type(PaymentType.fromName(response.getType()))
                        .method(PaymentMethod.fromValue(response.getMethod()))
                        .approvedAt(OffsetDateTime.parse(response.getApprovedAt()).toLocalDateTime())
                        .pspRawData(jsonString)
                        .orderName(response.getOrderName())
                        .pspConfirmationStatus(PSPConfirmationStatus.of(response.getStatus()))
                        .totalAmount((long) response.getTotalAmount())
                        .build())
                .isSuccess(true)
                .isFailure(false)
                .isUnknown(false)
                .isRetryable(false)
                .build();
    }
}
