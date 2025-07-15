package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, Long> {

}
