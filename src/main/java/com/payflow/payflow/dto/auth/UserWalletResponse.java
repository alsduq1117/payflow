package com.payflow.payflow.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UserWalletResponse {

    private BigDecimal balance;

    @Builder
    public UserWalletResponse(BigDecimal balance) {
        this.balance = balance;
    }
}
