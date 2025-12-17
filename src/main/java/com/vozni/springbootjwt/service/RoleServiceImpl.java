package com.vozni.springbootjwt.service;

import com.vozni.springbootjwt.model.Role;
import com.vozni.springbootjwt.repository.RoleDA;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleDA roleDA;
    public Role save(Role role){
        return roleDA.save(role);
    }

    public List<Role> getAll(){
        return roleDA.findAll();
    }
}
