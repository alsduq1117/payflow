package com.payflow.payflow.domain.wallet;

import com.payflow.payflow.domain.common.BaseEntity;
import com.payflow.payflow.domain.payment.PaymentOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "wallets")
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private BigDecimal balance;

    @Version
    private Integer version;

    @Builder
    public Wallet(Long userId, BigDecimal balance, Integer version) {
        this.userId = userId;
        this.balance = balance;
        this.version = version;
    }


    public List<WalletTransaction> calculateBalanceWith(List<PaymentOrder> paymentOrders) {
        if (paymentOrders == null || paymentOrders.isEmpty()) {
            return Collections.emptyList();
        }

        BigDecimal totalAmount = paymentOrders.stream()
                .map(order -> BigDecimal.valueOf(order.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.balance = this.balance.add(totalAmount);

        return paymentOrders.stream().map(order -> WalletTransaction.from(order, this.id)).collect(Collectors.toList());
    }


}
