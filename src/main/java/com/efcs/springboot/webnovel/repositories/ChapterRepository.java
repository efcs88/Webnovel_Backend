package com.efcs.springboot.webnovel.repositories;

import com.efcs.springboot.webnovel.entities.Chapter;
import com.efcs.springboot.webnovel.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    boolean existsByNovelId(Long novelId);
    List<Chapter> findByNovelId(Long novelId);
}
