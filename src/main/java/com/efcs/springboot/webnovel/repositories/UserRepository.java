package com.efcs.springboot.webnovel.repositories;

import com.efcs.springboot.webnovel.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
