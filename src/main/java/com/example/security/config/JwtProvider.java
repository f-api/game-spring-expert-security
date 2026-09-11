package com.example.security.config;

import com.example.security.auth.dto.AuthUser;
import com.example.security.user.entity.User;
import com.example.security.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final long accessTtlSeconds;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-ttl-seconds}") long accessTtlSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTtlSeconds = accessTtlSeconds;
    }

    public String createAccessToken(
            User user
    ) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(accessTtlSeconds);
        // 회원 정보와 만료 시각을 담아 JWT 발급
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public AuthUser parseToken(
            String token
    ) {
        try {
            // JWT 서명과 만료 검증
            Jws<Claims> signedClaims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            Claims claims = signedClaims.getPayload();
            String subject = claims.getSubject();
            long userId = Long.parseLong(subject);
            Role role = Role.valueOf(claims.get("role", String.class));
            // 검증한 회원 정보로 인증 객체 생성
            return new AuthUser(userId, role);
        } catch (JwtException | IllegalArgumentException | NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
