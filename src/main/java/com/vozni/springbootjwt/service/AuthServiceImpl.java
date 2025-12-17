package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public String getRefreshToken(String username){
        return jwtUtil.getRefreshToken(username);
    }

    public String rotateRefreshToken(String refreshToken){
        return jwtUtil.getRotateRefreshToken(refreshToken);
    }

    public String getAccessToken(UserEntity user){
        return jwtUtil.getAccessToken(user);
    }

    public String getAccessToken(String refreshToken){
        UserEntity user = getUser(refreshToken);
        return getAccessToken(user);
    }


    public UserEntity getUser(String token) {
        String username = jwtUtil.getUsername(token);
        return userService.get(username);
    }
}
