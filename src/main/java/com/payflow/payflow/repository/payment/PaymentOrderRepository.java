package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
}
