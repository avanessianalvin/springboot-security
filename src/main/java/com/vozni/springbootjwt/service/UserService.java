package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.dto.RegisterDto;
import com.vozni.springbootjwt.model.UserEntity;


public interface UserService {
    UserEntity save(RegisterDto registerDto);
    void remove(UserEntity user);
    UserEntity get(String username);
    UserEntity get(long id);

    boolean isUsernameTaken(String username);
}
