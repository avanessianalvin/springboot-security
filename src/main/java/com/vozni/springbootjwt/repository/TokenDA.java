package com.vozni.springbootjwt.repository;

import com.vozni.springbootjwt.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDA extends JpaRepository<TokenEntity,Long> {
    public Optional<TokenEntity> findByUsernameAndDeviceIdAndReplacedBy(String username, String deviceId, Long nextId);
}
