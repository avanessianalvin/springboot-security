package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.dto.TokenPair;
import com.vozni.springbootjwt.model.TokenEntity;
import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.repository.TokenDA;
import com.vozni.springbootjwt.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final TokenDA tokenDA;

    public TokenPair getTokenPairByUsername(String username){
        UserEntity user = userService.get(username);
        String deviceId = UUID.randomUUID().toString();
        String accessToken = jwtUtil.getAccessToken(user);
        TokenEntity refreshToken = jwtUtil.getRefreshToken(username,deviceId);
        persistToken(refreshToken);
        return new TokenPair(accessToken,refreshToken.getToken());
    }

    public TokenPair getTokenPairByRefreshToken(String token){
        String[] usernameAndDeviceId = jwtUtil.getUsernameAndDeviceId(token);
        UserEntity user = userService.get(usernameAndDeviceId[0]);
        String accessToken = jwtUtil.getAccessToken(user);
        TokenEntity refreshToken = jwtUtil.getRefreshToken(usernameAndDeviceId[0], usernameAndDeviceId[1]);
        persistToken(refreshToken);
        return new TokenPair(accessToken,refreshToken.getToken());
    }

    private void persistToken(TokenEntity token){
        TokenEntity prevToken = tokenDA.findByUsernameAndDeviceIdAndReplacedBy(token.getUsername(), token.getDeviceId(), null).orElse(null);
        tokenDA.save(token);
        if (prevToken!=null){
            prevToken.setReplacedBy(token.getId());
            tokenDA.save(prevToken);
        }
    }
    public UserEntity getUser(String token) {
        String username = jwtUtil.getUsername(token);
        return userService.get(username);
    }
}
