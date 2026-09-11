package com.example.security.post.dto;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class PostResponse {
    private final Long id;
    private final Long userId;
}
