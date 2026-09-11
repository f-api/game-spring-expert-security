package com.example.security.auth.dto;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class AccessTokenResponse {
    private final String accessToken;
}
