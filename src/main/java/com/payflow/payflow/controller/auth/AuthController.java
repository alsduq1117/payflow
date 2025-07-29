package com.payflow.payflow.controller.auth;

import com.payflow.payflow.dto.auth.TokenResponse;
import com.payflow.payflow.dto.auth.SignupRequest;
import com.payflow.payflow.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        authService.signup(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        TokenResponse tokenResponse = authService.reissueAccessToken(refreshToken);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);

    }
}
