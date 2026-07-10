package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.dto.chapter.ChapterRequest;
import com.efcs.springboot.webnovel.entities.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService {

    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
    List<Chapter> findByNovelId(Long id);
    Chapter create(Chapter chapter, Long novelId);
    Chapter update(Long id, ChapterRequest request);
    Optional<Chapter> deleteById(Long id);

}
