package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.dto.RegisterDto;
import com.vozni.springbootjwt.exception.UsernameAlreadyTakenException;
import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.repository.UserDA;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDA userDA;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserEntity save(RegisterDto registerDto) {

        if (isUsernameTaken(registerDto.username())) {
            throw new UsernameAlreadyTakenException();
        }
        Role userRole = roleService.get("USER");
        String encodedPassword = passwordEncoder.encode(registerDto.password());
        UserEntity user = new UserEntity(0L, registerDto.username(), encodedPassword, List.of(userRole));

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

    @Override
    public boolean isUsernameTaken(String username) {
        return userDA.findByUsername(username).orElse(null) != null;
    }
}
