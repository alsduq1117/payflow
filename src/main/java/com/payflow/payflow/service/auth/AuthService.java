package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    // 회원가입
    public User signup(String email, String password) {
        User user = User.createLocalUser(email, passwordEncoder.encode(password)
        );
        return userRepository.save(user);
    }
}
