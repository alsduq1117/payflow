package com.payflow.payflow.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.dto.auth.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(principal);
        String refreshToken = jwtUtil.generateRefreshToken(principal);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        TokenResponse tokenResponse = new TokenResponse(accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}
