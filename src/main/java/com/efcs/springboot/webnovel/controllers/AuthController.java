package com.efcs.springboot.webnovel.controllers;

import com.efcs.springboot.webnovel.dto.auth.LoginRequest;
import com.efcs.springboot.webnovel.dto.auth.RegisterRequest;
import com.efcs.springboot.webnovel.dto.auth.TokenResponse;
import com.efcs.springboot.webnovel.entities.User;
import com.efcs.springboot.webnovel.exception.EmailNotFoundException;
import com.efcs.springboot.webnovel.exception.InvalidPasswordException;
import com.efcs.springboot.webnovel.repositories.UserRepository;
import com.efcs.springboot.webnovel.service.AuthService;
import com.efcs.springboot.webnovel.service.AuthServiceImpl;
import com.efcs.springboot.webnovel.service.JwtService;
import com.efcs.springboot.webnovel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin({"http://localhostlocalhost:3000"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl service;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request) {
        final TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public  ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    return new EmailNotFoundException("Invalid credentials");
                });
        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new InvalidPasswordException("Invalid credentials");
        }
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader){
        return service.refreshToken(authHeader);
    }


}
