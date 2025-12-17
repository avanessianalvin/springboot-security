package com.vozni.springbootjwt.repository;

import com.vozni.springbootjwt.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDA extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
