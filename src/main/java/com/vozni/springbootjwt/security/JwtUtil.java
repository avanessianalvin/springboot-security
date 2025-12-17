package com.vozni.springbootjwt.security;

import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {


    public JwtUtil(JwtProperties jwtProperties){
        this.secretKey =  Keys.hmacShaKeyFor(jwtProperties.secret.getBytes());
        this.accessTokenDuration = jwtProperties.accessTokenDuration;
        this.refreshTokenDuration = jwtProperties.refreshTokenDuration;
    }
    private final SecretKey secretKey;
    private final int accessTokenDuration;
    private final int refreshTokenDuration;


    public String getAccessToken(UserEntity user){
        Instant instant = Instant.now();
        return Jwts.builder()
                .signWith(secretKey)
                .subject(user.getUsername())
                .claims(
                        Map.of(
                                "roles",user.getRoles().stream().map(Role::getName).toList()
                        )
                )
                .issuedAt(Date.from(instant))
                .expiration(Date.from(instant.plusSeconds(accessTokenDuration)))
                .compact();
    }

    public String getRefreshToken(String username){
        String deviceId = UUID.randomUUID().toString();
        return getRefreshToken(username,deviceId);
    }

    public String getRotateRefreshToken(String refreshToken){
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        String username = claims.getSubject();
        String deviceId = (String) claims.get("deviceId");
        return getRefreshToken(claims.getSubject(),deviceId);
    }
    public String getRefreshToken(String username, String deviceId){
        Instant instant = Instant.now();
        return Jwts.builder()
                .signWith(secretKey)
                .subject(username)
                .issuedAt(Date.from(instant))
                .claims(
                        Map.of("deviceId",deviceId)
                )
                .expiration(Date.from(instant.plusSeconds(refreshTokenDuration)))
                .compact();
    }

    public String getUsername(String token){
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();

        }catch (Exception e) {
            throw new JwtException("invalid or expired token");
        }
    }
}
