package com.efcs.springboot.webnovel.service;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements  NovelService{

    private final NovelRepository repository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;


    @Override
    @Transactional
    public List<Novel> findAll() {
        return (List<Novel>)  repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Novel> findById(Long id){
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Novel> findByUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return repository.findByUser(user);
    }
    @Override
    @Transactional
    public Novel save(Novel novel, MultipartFile imagen){
        if (imagen != null && !imagen.isEmpty()){
            String imageName = storageService.save(imagen,novel.getTitle());
            novel.setCoverImage(imageName);
        }
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        novel.setUser(user);
        return repository.save(novel);
    }

    @Override
    @Transactional
    public Optional<Novel> deleteById(Long id){
        Optional<Novel> novelOptional = repository.findById(id);
        if (novelOptional.isPresent()){
            if (chapterRepository.existsByNovelId(id)){
                throw  new IllegalStateException(
                        "No se puede eliminar una novela que tiene capítulos"
                );
            }
            repository.deleteById(id);
            return novelOptional;
        }
        return Optional.empty();
    }


}
