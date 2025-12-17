package com.vozni.springbootjwt.controller.dto;

import java.util.List;

public record UserDto(
        String  username,
        List<String> roles) {
}
