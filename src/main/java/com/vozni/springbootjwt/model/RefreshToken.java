package com.vozni.springbootjwt.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String token;
    String username;
    UUID deviceId;
    Date issueDate;
    Date expireDate;
    long nextId;

}
