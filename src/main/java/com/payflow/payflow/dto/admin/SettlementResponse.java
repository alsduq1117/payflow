package com.payflow.payflow.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SettlementResponse {

    private Long sellerId;

    private String nickname;

    private Long orderCount;

    private Long gross;

    private Long fee;

    private Long net;

    private LocalDateTime lastPayoutAt;

    @Builder
    public SettlementResponse(Long sellerId, String nickname, Long orderCount, Long gross, Long fee, Long net, LocalDateTime lastPayoutAt) {
        this.sellerId = sellerId;
        this.nickname = nickname;
        this.orderCount = orderCount;
        this.gross = gross;
        this.fee = fee;
        this.net = net;
        this.lastPayoutAt = lastPayoutAt;
    }
}