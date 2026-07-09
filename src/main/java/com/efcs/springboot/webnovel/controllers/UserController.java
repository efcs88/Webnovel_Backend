package com.efcs.springboot.webnovel.controllers;

import com.efcs.springboot.webnovel.dto.user.UserRequest;
import com.efcs.springboot.webnovel.dto.user.UserResponse;
import com.efcs.springboot.webnovel.entities.User;
import com.efcs.springboot.webnovel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin({"http://localhost:8080"})
@RequiredArgsConstructor
public class UserController {

    final private UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> users = service.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> details(@PathVariable Long id){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());

        User savedUser = service.save(user);
        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest, @PathVariable Long id){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()){
            User userDb = optionalUser.orElseThrow();
            userDb.setEmail(userRequest.email());
            userDb.setUsername(userRequest.username());
            User userSaved = service.save(userDb);
            UserResponse userResponse = new UserResponse(
                userSaved.getId(),
                userSaved.getUsername(),
                userSaved.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id){
        Optional<User> optionalUser = service.deleteById(id);
        if (optionalUser.isPresent()){
            User userDeleted = optionalUser.orElseThrow();
            UserResponse userResponse = new UserResponse(
                    userDeleted.getId(),
                    userDeleted.getUsername(),
                    userDeleted.getEmail()
            );
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
