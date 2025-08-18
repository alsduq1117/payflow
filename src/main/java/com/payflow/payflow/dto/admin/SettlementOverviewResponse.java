package com.payflow.payflow.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementOverviewResponse {

    private long gross;
    private long net;
    private long orderCount;
    private long pending;

    @Builder
    public SettlementOverviewResponse(long gross, long net, long orderCount, long pending) {
        this.gross = gross;
        this.net = net;
        this.orderCount = orderCount;
        this.pending = pending;
    }
}
