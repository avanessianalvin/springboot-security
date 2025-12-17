package com.vozni.springbootjwt.dto;

import java.util.List;

public record UserDto(
        String  username,
        List<String> roles) {
}
