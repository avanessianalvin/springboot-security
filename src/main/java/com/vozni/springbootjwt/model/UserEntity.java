package com.vozni.springbootjwt.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
                ,joinColumns = @JoinColumn(name = "user_id")
                ,inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;
}
