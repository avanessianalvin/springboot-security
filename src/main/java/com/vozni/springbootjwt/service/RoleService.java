package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);
    List<Role> getAll();
}
