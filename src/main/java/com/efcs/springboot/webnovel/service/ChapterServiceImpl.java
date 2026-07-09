package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.entities.Chapter;
import com.efcs.springboot.webnovel.repositories.ChapterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService{

    final private ChapterRepository repository;

    public ChapterServiceImpl(ChapterRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Chapter> findAll() {
        return (List<Chapter>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Chapter> findById(Long id){
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Chapter save(Chapter chapter) {
        return repository.save(chapter);
    }

    @Override
    @Transactional
    public Optional<Chapter> deleteById(Long id){
        Optional<Chapter> chapterOptional = repository.findById(id);
        if (chapterOptional.isPresent()){
            repository.deleteById(id);
            return chapterOptional;
        }
        return Optional.empty();
    }

}
