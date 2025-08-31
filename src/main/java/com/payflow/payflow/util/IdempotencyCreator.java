package com.payflow.payflow.util;

import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.dto.payment.CheckoutRequest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

public final class IdempotencyCreator {

    private IdempotencyCreator() {
    }

    // 공통 UUID 변환 로직
    private static String generateKey(String raw) {
        return UUID.nameUUIDFromBytes(raw.getBytes(StandardCharsets.UTF_8)).toString();
    }

    /**
     * PaymentOrder 기반 키 생성
     */
    public static String create(PaymentOrder order) {
        String raw = order.getOrderId() + ":" + order.getSellerId() + ":" + order.getProductId() + ":" + order.getAmount();
        return generateKey(raw);
    }

    /**
     * CheckoutRequest 기반 키 생성
     */
    public static String create(CheckoutRequest request) {
        String productPart = request.getProductIds().stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));

        String raw = request.getBuyerId() + ":" + productPart + ":" + request.getSeed();
        return generateKey(raw);
    }
}