package com.efcs.springboot.webnovel.dto.auth;

public record LoginRequest (
        String email,
        String password
){
}
