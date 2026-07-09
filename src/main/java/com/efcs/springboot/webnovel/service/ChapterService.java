package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.entities.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService {

    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
    Chapter save(Chapter chapter);
    Optional<Chapter> deleteById(Long id);

}
