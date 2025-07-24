package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    List<PaymentOrder> findByOrderId(String orderId);
}
