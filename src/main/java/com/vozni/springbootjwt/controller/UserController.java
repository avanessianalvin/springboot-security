package com.vozni.springbootjwt.controller;

import com.vozni.springbootjwt.util.UserUtils;
import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<?> getUser(SecurityContext securityContext){
        String principal = (String) securityContext.getAuthentication().getPrincipal();
        UserEntity user = userService.get(principal);
        return ResponseEntity.ok(UserUtils.mapUserEntityToUserDto(user));
    }
}
