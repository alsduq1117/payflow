package com.payflow.payflow.domain.ledger;

import com.payflow.payflow.domain.common.BaseOnlyCreated;
import com.payflow.payflow.repository.wallet.ReferenceType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ledger_transactions")
public class LedgerTransaction extends BaseOnlyCreated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    private String description;


    @Builder
    public LedgerTransaction(ReferenceType referenceType, Long referenceId, String orderId, String idempotencyKey, String description) {
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.orderId = orderId;
        this.idempotencyKey = idempotencyKey;
        this.description = description;
    }
}
