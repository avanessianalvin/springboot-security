package com.vozni.springbootjwt.repository;

import com.vozni.springbootjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDA extends JpaRepository<Role,Integer> {
}
