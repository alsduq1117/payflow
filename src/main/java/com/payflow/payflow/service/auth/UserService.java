package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.auth.UserProfileResponse;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
