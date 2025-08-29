package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.domain.wallet.Wallet;
import com.payflow.payflow.dto.auth.TokenResponse;
import com.payflow.payflow.exception.UnAuthorizedException;
import com.payflow.payflow.exception.auth.UserNotFoundException;
import com.payflow.payflow.repository.auth.UserRepository;
import com.payflow.payflow.repository.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public User signup(String email, String password) {
        User user = userRepository.save(User.createLocalUser(email, passwordEncoder.encode(password)));
        walletRepository.save(Wallet.createFor(user.getId()));
        return user;
    }

    @Transactional
    public TokenResponse reissueAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) throw new UnAuthorizedException("유효하지 않은 refresh token 입니다.");
        String email = jwtUtil.getUsernameFromToken(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        String newAccessToken = jwtUtil.generateAccessToken(email, user.getRoles(), user.getProvider());
        return new TokenResponse(newAccessToken);
    }

    public ResponseCookie createLogoutCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
    }
}
