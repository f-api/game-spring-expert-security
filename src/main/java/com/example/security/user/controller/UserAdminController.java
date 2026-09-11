package com.example.security.user.controller;

import com.example.security.user.dto.SignupRequest;
import com.example.security.user.dto.UserResponse;
import com.example.security.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @PostMapping("/admin/signup")
    public ResponseEntity<UserResponse> signupAdmin(
            @RequestBody SignupRequest request
    ) {
        UserResponse user = userService.signupAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> users() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponse>> adminUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
