package com.vozni.springbootjwt.util;

import com.vozni.springbootjwt.controller.dto.UserDto;
import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserUtils {
    public static UserDto mapUserEntityToUserDto(UserEntity user){
        return new UserDto(user.getUsername(),user.getRoles().stream().map(Role::getName).toList());
    }

    public static List<? extends GrantedAuthority> getGrantedAuthorities(UserEntity user){
        return user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList();
    }
}
