package com.vozni.springbootjwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    Integer id;
    String name;
}
