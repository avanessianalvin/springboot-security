package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.repository.UserDA;
import com.vozni.springbootjwt.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDA userDA;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDA.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(UserUtils.getGrantedAuthorities(userEntity))
                .build();
    }
}
