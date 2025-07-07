package com.payflow.payflow.service;

import com.payflow.payflow.domain.*;
import com.payflow.payflow.dto.response.CheckoutResponse;
import com.payflow.payflow.repository.PaymentEventRepository;
import com.payflow.payflow.repository.PaymentOrderRepository;
import com.payflow.payflow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductRepository productRepository;
    private final PaymentEventRepository paymentEventRepository;
    private final PaymentOrderRepository paymentOrderRepository;

    @Transactional
    public CheckoutResponse checkout(CheckoutCommand command) {
        List<Product> products = productRepository.findAllById(command.getProductIds());

        PaymentEvent paymentEvent = createPaymentEvent(command, products);

        List<PaymentOrder> paymentOrders = createPaymentOrders(command, products, paymentEvent.getId());

        long totalAmount = paymentOrders.stream()
                .mapToLong(PaymentOrder::getAmount)
                .sum();


        return CheckoutResponse.builder()
                .amount(totalAmount)
                .orderId(paymentEvent.getOrderId())
                .orderName(paymentEvent.getOrderName())
                .build();
    }


    private PaymentEvent createPaymentEvent(CheckoutCommand command, List<Product> products) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .buyerId(command.getBuyerId())
                .orderId(command.getIdempotencyKey())
                .orderName(products.stream()
                        .map(Product::getName)
                        .collect(Collectors.joining(", ")))
                .paymentType(PaymentType.NORMAL)
                .isPaymentDone(false)
                .build();

        return paymentEventRepository.save(paymentEvent);
    }

    private List<PaymentOrder> createPaymentOrders(CheckoutCommand command, List<Product> products, Long paymentEventId) {
        List<PaymentOrder> paymentOrders = products.stream()
                .map(product -> PaymentOrder.builder()
                        .sellerId(product.getSellerId())
                        .paymentEventId(paymentEventId)
                        .orderId(command.getIdempotencyKey())  // 공통 키로 연결
                        .productId(product.getId())
                        .amount(product.getPrice().longValue())
                        .paymentStatus(PaymentStatus.NOT_STARTED)
                        .build())
                .collect(Collectors.toList());

        return paymentOrderRepository.saveAll(paymentOrders);
    }
}
