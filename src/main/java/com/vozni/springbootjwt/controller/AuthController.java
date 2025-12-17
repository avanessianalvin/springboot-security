package com.vozni.springbootjwt.controller;

import com.vozni.springbootjwt.dto.LoginDto;
import com.vozni.springbootjwt.dto.RegisterDto;
import com.vozni.springbootjwt.dto.TokenPair;
import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.security.JwtProperties;
import com.vozni.springbootjwt.service.AuthServiceImpl;
import com.vozni.springbootjwt.service.RoleService;
import com.vozni.springbootjwt.service.UserService;
import com.vozni.springbootjwt.util.UserUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth") public class AuthController {

    private final AuthServiceImpl authService;

    private final JwtProperties jwtProperties;
    private final static String REFRESH_TOKEN = "refresh_token";

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        Role userRole = roleService.get("USER");
        String encodedPassword = passwordEncoder.encode(registerDto.password());
        UserEntity user = new UserEntity(0L, registerDto.username(), encodedPassword, List.of(userRole));
        userService.save(user);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        //UserEntity user = new UserEntity(null, loginDto.username(), loginDto.password(), new ArrayList<>());
        UserEntity userEntity = userService.get(loginDto.username());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(),
                        userEntity.getPassword(),
                        UserUtils.getGrantedAuthorities(userEntity));
        authenticationManager.authenticate(authenticationToken);
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

