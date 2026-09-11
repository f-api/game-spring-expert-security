package com.example.security.post.service;

import com.example.security.user.entity.User;
import com.example.security.user.repository.UserRepository;
import com.example.security.post.dto.PostResponse;
import com.example.security.post.entity.Post;
import com.example.security.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse create(
            long authenticatedUserId
    ) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Post post = new Post(user);
        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost.getId(), savedPost.getUser().getId());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post.getId(), post.getUser().getId()))
                .toList();
    }

    @Transactional
    public void delete(
            long postId,
            long authenticatedUserId
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (post.getUser().getId() != authenticatedUserId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        postRepository.delete(post);
    }
}
