package com.payflow.payflow.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String nickname;
    private Long balance;

    @Builder
    public UserProfileResponse(Long id, String nickname, Long balance) {
        this.id = id;
        this.nickname = nickname;
        this.balance = balance;
    }
}
