package com.payflow.payflow.repository.auth;

import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import com.payflow.payflow.dto.auth.UserProfileResponse;
import com.payflow.payflow.dto.auth.UserWalletResponse;

import java.util.List;

public interface UserRepositoryCustom {

    List<OrderSummaryResponse> getMyOrders(Long userId);

    UserWalletResponse getUserWallet(Long userId);
}
