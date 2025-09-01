package com.payflow.payflow.controller.auth;

import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import com.payflow.payflow.dto.auth.UserProfileResponse;
import com.payflow.payflow.dto.auth.UserWalletResponse;
import com.payflow.payflow.service.auth.UserPrincipal;
import com.payflow.payflow.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserProfileResponse userProfileResponse = userService.getUserProfile(userPrincipal.getUser().getId());
        return ResponseEntity.ok(userProfileResponse);
    }

    @GetMapping("/me/wallet")
    public ResponseEntity<UserWalletResponse> getUserWallet(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserWalletResponse userWalletResponse = userService.getUserWallet(userPrincipal.getUser().getId());
        return ResponseEntity.ok(userWalletResponse);
    }

    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderSummaryResponse>> getMyOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<OrderSummaryResponse> orderSummaryResponseList = userService.getMyOrders(userPrincipal.getUser().getId());
        return ResponseEntity.ok(orderSummaryResponseList);
    }
}
