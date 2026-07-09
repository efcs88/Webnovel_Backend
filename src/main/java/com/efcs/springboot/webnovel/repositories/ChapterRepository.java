package com.efcs.springboot.webnovel.repositories;

import com.efcs.springboot.webnovel.entities.Chapter;
import org.springframework.data.repository.CrudRepository;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    boolean existsByNovelId(Long novelId);
}
