package com.efcs.springboot.webnovel.dto.user;

public record UserRequest (
        Long id,
        String username,
        String email,
        String password
){
}
