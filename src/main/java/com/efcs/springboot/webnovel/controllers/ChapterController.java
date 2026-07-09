package com.efcs.springboot.webnovel.controllers;

import com.efcs.springboot.webnovel.entities.Chapter;
import com.efcs.springboot.webnovel.service.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chapters")
@CrossOrigin({"http://localhost:8080/login"})
public class ChapterController {


    final private ChapterService service;

    public ChapterController(ChapterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Chapter>> list(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Chapter> details(@PathVariable Long id){
        Optional<Chapter> optionalChapter = service.findById(id);
        if (optionalChapter.isPresent()){
            return ResponseEntity.ok(optionalChapter.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id}")
    public ResponseEntity<Chapter> create(@RequestBody Chapter chapter, @PathVariable Long id){
        Optional<Chapter> optionalChapter = service.findById(id);
        if (optionalChapter.isPresent()){
            Chapter chapterDb = optionalChapter.orElseThrow();
            chapterDb.setContent(chapterDb.getContent());
            chapterDb.setTitle(chapterDb.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(chapterDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Chapter> delete(@PathVariable Long id){
        Optional<Chapter> optionalChapter = service.deleteById(id);
        if (optionalChapter.isPresent()){
            Chapter chapterDeleted = optionalChapter.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(chapterDeleted);
        }
        return ResponseEntity.notFound().build();
    }
}
