package com.payflow.payflow.controller.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.auth.SignupRequest;
import com.payflow.payflow.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
        User user = authService.signup(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new SignupResponse(user.getId(), user.getEmail()));
    }


    public record SignupResponse(Long id, String email) {}
}
