package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.entities.Token;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    List<Token> findAll();
    Optional<Token> findById(Long id);
    Token save(Token chapter);
    Optional<Token> deleteById(Long id);
    void logout(String authHeader);
}
