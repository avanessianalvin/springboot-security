package com.vozni.springbootjwt.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "token")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@Data
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String token;
    String username;
    String deviceId;
    Date issueDate;
    Date expireDate;
    Long replacedBy;

}
