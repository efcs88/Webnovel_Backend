package com.efcs.springboot.webnovel.dto.user;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
