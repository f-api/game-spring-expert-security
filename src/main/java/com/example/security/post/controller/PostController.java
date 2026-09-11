package com.example.security.post.controller;

import com.example.security.auth.dto.AuthUser;
import com.example.security.post.dto.PostResponse;
import com.example.security.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> create(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        PostResponse post = postService.create(authUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> posts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        postService.delete(id, authUser.getUserId());
        return ResponseEntity.noContent().build();
    }
}
