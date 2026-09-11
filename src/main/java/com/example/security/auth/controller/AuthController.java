package com.example.security.auth.controller;

import com.example.security.auth.dto.AccessTokenResponse;
import com.example.security.auth.dto.LoginRequest;
import com.example.security.auth.service.JwtAuthService;
import com.example.security.user.dto.SignupRequest;
import com.example.security.user.dto.UserResponse;
import com.example.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtAuthService jwtAuthService;
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponse> signup(
            @RequestBody SignupRequest request
    ) {
        UserResponse user = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(jwtAuthService.login(request));
    }
}
