package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.entities.Novel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface NovelService {

    List<Novel> findAll();
    Optional<Novel> findById(Long id);
    List<Novel> findByUser();
    Novel save(Novel novel, MultipartFile imagen);
    Optional<Novel> deleteById(Long id);

}
