package com.payflow.payflow.repository.payment;

import com.payflow.payflow.domain.payment.PaymentOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    List<PaymentOrder> findByOrderId(String orderId);

    @Modifying
    @Query("update PaymentOrder po set po.isLedgerUpdated = true where po.orderId = :orderId")
    void markLedgerUpdated(@Param("orderId") String orderId);

    @Modifying
    @Query("update PaymentOrder po set po.isWalletUpdated = true where po.orderId = :orderId")
    void markWalletUpdated(@Param("orderId") String orderId);
}
