package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, Long> {

    Optional<PaymentEvent> findByOrderId(String orderId);

    @Modifying
    @Query("update PaymentEvent pe set pe.isPaymentDone = true where pe.orderId = :orderId")
    void markPaymentDone(@Param("orderId") String orderId);
}
