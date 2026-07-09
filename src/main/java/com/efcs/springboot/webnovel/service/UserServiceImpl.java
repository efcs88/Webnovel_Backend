package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.entities.User;
import com.efcs.springboot.webnovel.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final private UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<User> findAll(){

        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id){
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user){
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> deleteById(Long id){
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()){
            repository.deleteById(id);
            return  userOptional;
        }
        return Optional.empty();
    }
}
