package com.payflow.payflow.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // 공통 JWT 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(principal);
        String refreshToken = jwtUtil.generateRefreshToken(principal);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")  // Cross-Origin 환경이면 필요
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();

        // 3. 액세스 토큰 → URL Fragment로 전달
        String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .fragment("access_token=" + URLEncoder.encode(accessToken, "UTF-8")
                        + "&provider=" + principal.getProvider().name())
                .build().toUriString();

        // 4. 응답 설정
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.sendRedirect(redirectUrl);
    }
}
