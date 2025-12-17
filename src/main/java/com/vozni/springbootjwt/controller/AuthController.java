package com.vozni.springbootjwt.controller;

import com.vozni.springbootjwt.dto.TokenPair;
import com.vozni.springbootjwt.security.JwtProperties;
import com.vozni.springbootjwt.service.AuthServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth") public class AuthController {

    private final AuthServiceImpl authService;

    private final JwtProperties jwtProperties;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        //UserEntity user = new UserEntity(null, loginDto.username(), loginDto.password(), new ArrayList<>());
        TokenPair tokenPair = authService.getTokenPairByUsername(loginDto.username());

        Cookie refreshTokenCookie = getRefreshTokenCookie(tokenPair.refreshToken());
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(tokenPair.accessToken());
    }

    @GetMapping("/token")
    public ResponseEntity<?> accessToken(
            @CookieValue(value = REFRESH_TOKEN, required = false) Cookie cookie,
            HttpServletResponse response){
        // check cookie and token validity
        if (cookie==null || cookie.getValue().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            TokenPair tokenPair = authService.getTokenPairByRefreshToken(cookie.getValue());
            Cookie refreshTokenCookie = getRefreshTokenCookie(tokenPair.refreshToken());
            response.addCookie(refreshTokenCookie);
            return ResponseEntity.ok(tokenPair.accessToken());
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    private Cookie getRefreshTokenCookie(String token){
        Cookie cookie = new Cookie(REFRESH_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(jwtProperties.getRefreshTokenDuration());
        return cookie;
    }

}

record LoginDto(String username,String password){}
