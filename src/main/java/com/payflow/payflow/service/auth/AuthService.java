package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(String email, String password) {
        User user = User.createLocalUser(email, passwordEncoder.encode(password));
        log.info("password : " + passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
