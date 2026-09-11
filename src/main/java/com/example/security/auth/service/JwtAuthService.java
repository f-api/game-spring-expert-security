package com.example.security.auth.service;

import com.example.security.auth.dto.AccessTokenResponse;
import com.example.security.auth.dto.LoginRequest;
import com.example.security.config.JwtProvider;
import com.example.security.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtAuthService {
    private final CredentialService credentialService;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public AccessTokenResponse login(
            LoginRequest request
    ) {
        User user = credentialService.authenticate(request);
        String accessToken = jwtProvider.createAccessToken(user);
        return new AccessTokenResponse(accessToken);
    }
}
