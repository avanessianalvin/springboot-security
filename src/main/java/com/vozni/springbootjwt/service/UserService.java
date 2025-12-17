package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.UserEntity;


public interface UserService {
    UserEntity save(UserEntity user);
    void remove(UserEntity user);
    UserEntity get(String username);
    UserEntity get(long id);
}
