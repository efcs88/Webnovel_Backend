package com.efcs.springboot.webnovel.controllers;

import com.efcs.springboot.webnovel.dto.chapter.ChapterRequest;
import com.efcs.springboot.webnovel.dto.chapter.ChapterResponse;
import com.efcs.springboot.webnovel.entities.Chapter;
import com.efcs.springboot.webnovel.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chapters")
@CrossOrigin({"http://localhost:8080/login"})
@RequiredArgsConstructor
public class ChapterController {


    final private ChapterService service;

    @GetMapping
    public ResponseEntity<List<ChapterResponse>> list(){
        List<ChapterResponse> chapters = service.findAll()
                .stream()
                .map(chapter -> new ChapterResponse(
                        chapter.getId(),
                        chapter.getTitle(),
                        chapter.getContent()
                ))
                .toList();

        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/novel/{novelId}")
    public ResponseEntity<List<ChapterResponse>> listChapters(@PathVariable Long novelId, Authentication authentication) {
        List<ChapterResponse> chapters = service.findByNovelId(novelId)
                .stream()
                .map(chapter -> new ChapterResponse(
                        chapter.getId(),
                        chapter.getTitle(),
                        chapter.getContent()
                ))
                .toList();
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("{id}")
    public ResponseEntity<ChapterResponse> details(@PathVariable Long id){
        Optional<Chapter> optionalChapter = service.findById(id);
        if (optionalChapter.isPresent()){
            Chapter chapter = optionalChapter.orElseThrow();
            ChapterResponse chapterResponse = new ChapterResponse(
                    chapter.getId(), chapter.getTitle(),
                    chapter.getContent()
            );
            return ResponseEntity.ok(chapterResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ChapterResponse> create(@RequestBody ChapterRequest request){
        Chapter chapter = new Chapter();
        chapter.setTitle(request.title());
        chapter.setContent("");
        System.out.println("titulo"+request.title());
        System.out.println("Novel id"+request.novelId());

        Chapter savedChapter = service.create(chapter, request.novelId());

        ChapterResponse response = new ChapterResponse(
                savedChapter.getId(),
                savedChapter.getContent(),
                savedChapter.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ChapterResponse> update(@PathVariable Long id, @RequestBody ChapterRequest request) {
        Chapter saved = service.update(id, request);
        return ResponseEntity.ok( new ChapterResponse(saved.getId(), saved.getTitle(),saved.getContent()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ChapterResponse> delete(@PathVariable Long id){
        Optional<Chapter> optionalChapter = service.deleteById(id);
        if (optionalChapter.isPresent()){
            Chapter chapterDeleted = optionalChapter.orElseThrow();
            ChapterResponse chapterResponse = new ChapterResponse(
              chapterDeleted.getId(),
              chapterDeleted.getTitle(),
              chapterDeleted.getContent()
            );
            return ResponseEntity.status(HttpStatus.OK).body(chapterResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
