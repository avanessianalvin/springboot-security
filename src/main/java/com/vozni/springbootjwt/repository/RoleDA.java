package com.vozni.springbootjwt.repository;

import com.vozni.springbootjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDA extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
