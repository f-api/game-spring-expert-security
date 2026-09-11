package com.example.security.user.service;

import com.example.security.user.dto.UserResponse;
import com.example.security.user.dto.SignupRequest;
import com.example.security.user.entity.User;
import com.example.security.user.entity.Role;
import com.example.security.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(
            SignupRequest request
    ) {
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(request.getEmail(), encodedPassword, Role.USER);
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    @Transactional
    public UserResponse signupAdmin(
            SignupRequest request
    ) {
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(request.getEmail(), encodedPassword, Role.ADMIN);
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    @Transactional(readOnly = true)
    public UserResponse findMe(
            long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getRole()))
                .toList();
    }
}
