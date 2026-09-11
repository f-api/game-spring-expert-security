package com.example.security.auth.dto;

import com.example.security.user.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthUser {
    private final long userId;
    private final Role role;
}
