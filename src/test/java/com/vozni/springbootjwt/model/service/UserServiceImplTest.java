package com.vozni.springbootjwt.model.service;

import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.service.RoleService;
import com.vozni.springbootjwt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Test
    void save() {
        List<Role> roles = roleService.getAll();
        UserEntity user = new UserEntity(null,"user4","password",roles.subList(1,2));
        userService.save(user);
        userService.remove(user);
    }
}