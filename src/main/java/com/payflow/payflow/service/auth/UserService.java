package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.admin.OrderSummaryResponse;
import com.payflow.payflow.dto.auth.UserProfileResponse;
import com.payflow.payflow.dto.auth.UserWalletResponse;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserProfileResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getMyOrders(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userRepository.getMyOrders(userId);
    }

    @Transactional(readOnly = true)
    public UserWalletResponse getUserWallet(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userRepository.getUserWallet(userId);
    }
}
