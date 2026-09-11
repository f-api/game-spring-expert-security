package com.example.security.user.dto;

import com.example.security.user.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class UserResponse {
    private final Long id;
    private final String email;
    private final Role role;
}
