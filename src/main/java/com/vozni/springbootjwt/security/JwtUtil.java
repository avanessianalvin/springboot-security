package com.vozni.springbootjwt.security;

import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.TokenEntity;
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


    public TokenEntity getRefreshToken(String username, String deviceId){
        Instant instant = Instant.now();
        Date issueDate = Date.from(instant);
        Date expireDate = Date.from(instant.plusSeconds(refreshTokenDuration));
        String token =  Jwts.builder()
                .signWith(secretKey)
                .subject(username)
                .issuedAt(issueDate)
                .claims(
                        Map.of("deviceId",deviceId)
                )
                .expiration(expireDate)
                .compact();

        return new TokenEntity()
                .setToken(token)
                .setUsername(username)
                .setIssueDate(issueDate)
                .setExpireDate(expireDate)
                .setDeviceId(deviceId);
    }


    public String getUsername(String token){
        System.out.println(token);
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();

        }catch (Exception e) {
            throw new JwtException("invalid or expired token");
        }
    }

    public String[] getUsernameAndDeviceId(String token){
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new String[]{claims.getSubject(),claims.get("deviceId").toString()};

        }catch (Exception e) {
            throw new JwtException("invalid or expired token");
        }
    }
}
