package com.efcs.springboot.webnovel.dto.auth;

public record RegisterRequest(
        String email,
        String password,
        String username
){

}