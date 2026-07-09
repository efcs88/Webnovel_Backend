package com.efcs.springboot.webnovel.repositories;

import com.efcs.springboot.webnovel.entities.Novel;
import com.efcs.springboot.webnovel.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NovelRepository extends CrudRepository<Novel, Long> {
    List<Novel> findByUser(User user);
}
