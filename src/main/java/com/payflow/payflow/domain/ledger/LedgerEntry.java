package com.payflow.payflow.domain.ledger;

import com.payflow.payflow.domain.common.BaseOnlyCreated;
import com.payflow.payflow.domain.payment.PaymentOrder;
import com.payflow.payflow.repository.wallet.ReferenceType;
import com.payflow.payflow.util.IdempotencyCreator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ledger_entries")
public class LedgerEntry extends BaseOnlyCreated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    private BigDecimal amount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LedgerEntryType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transaction_id", nullable = false)
    private LedgerTransaction transaction;


    @Builder
    public LedgerEntry(Long accountId, BigDecimal amount, LedgerEntryType type, LedgerTransaction transaction) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.transaction = transaction;
    }



    public static List<DoubleLedgerEntry> createDoubleLedgerEntry(DoubleAccountsForLedger doubleAccountsForLedger, List<PaymentOrder> paymentOrders) {
        return paymentOrders.stream().map(paymentOrder -> {
            LedgerTransaction transaction = LedgerTransaction.builder()
                    .referenceType(ReferenceType.PAYMENT_ORDER)
                    .referenceId(paymentOrder.getId())
                    .orderId(paymentOrder.getOrderId())
                    .description("LedgerService record transaction")
                    .idempotencyKey(IdempotencyCreator.create(paymentOrder))
                    .build();

            LedgerEntry credit = LedgerEntry.builder()
                    .accountId(doubleAccountsForLedger.getTo().getId())
                    .amount(BigDecimal.valueOf(paymentOrder.getAmount()))
                    .type(LedgerEntryType.CREDIT)
                    .transaction(transaction)
                    .build();

            LedgerEntry debit = LedgerEntry.builder()
                    .accountId(doubleAccountsForLedger.getFrom().getId())
                    .amount(BigDecimal.valueOf(paymentOrder.getAmount()))
                    .type(LedgerEntryType.DEBIT)
                    .transaction(transaction)
                    .build();

            return DoubleLedgerEntry.builder()
                    .transaction(transaction)
                    .credit(credit)
                    .debit(debit)
                    .build();
        }).collect(Collectors.toList());

    }
}
