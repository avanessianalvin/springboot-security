package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.repository.UserDA;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDA userDA;
    @Override
    public UserEntity save(UserEntity user) {
        user = userDA.save(user);
        return user;
    }

    @Override
    public void remove(UserEntity user) {
        userDA.delete(user);
    }

    @Override
    public UserEntity get(String username) {
        return userDA.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserEntity get(long id) {
        return userDA.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
}
