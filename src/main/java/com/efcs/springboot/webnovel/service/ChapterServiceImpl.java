package com.efcs.springboot.webnovel.service;

import com.efcs.springboot.webnovel.dto.chapter.ChapterRequest;
import com.efcs.springboot.webnovel.entities.Chapter;
import com.efcs.springboot.webnovel.entities.Novel;
import com.efcs.springboot.webnovel.entities.User;
import com.efcs.springboot.webnovel.repositories.ChapterRepository;
import com.efcs.springboot.webnovel.repositories.NovelRepository;
import com.efcs.springboot.webnovel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService{

    private final ChapterRepository repository;
    private final UserRepository userRepository;
    private final NovelRepository novelRepository;

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
    @Transactional(readOnly = true)
    public List<Chapter> findByNovelId(Long novelId) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new RuntimeException("Novela no encontrada"));

        if (!novel.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para acceder a esta novela");
        }

        return repository.findByNovelId(novelId);
    }

    @Override
    @Transactional
    public Chapter create(Chapter chapter, Long novelId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new RuntimeException("Novela no encontrada"));
        if (!novel.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso");
        }
        chapter.setNovel(novel);

        return repository.save(chapter);
    }

    @Override
    @Transactional
    public Chapter update(Long id, ChapterRequest request) {
        Chapter chapter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!chapter.getNovel().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso");
        }
        chapter.setTitle(request.title());
        chapter.setContent(request.content());

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
