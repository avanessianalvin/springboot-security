package com.vozni.springbootjwt.controller;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        //UserEntity user = new UserEntity(null, loginDto.username(), loginDto.password(), new ArrayList<>());
        String token = authService.getRefreshToken(loginDto.username());

        Cookie cookie = new Cookie("refresh_token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(jwtProperties.getRefreshTokenDuration());

        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/token")
    public ResponseEntity<?> accessToken(@CookieValue(value = "refresh_token", required = false) Cookie cookie){
        // check cookie and token validity
        if (cookie==null || cookie.getValue().isBlank())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            String accessToken = authService.getAccessToken(cookie.getValue());
            return ResponseEntity.ok(accessToken);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}

record LoginDto(String username,String password){}
