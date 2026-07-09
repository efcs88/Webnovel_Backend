package com.efcs.springboot.webnovel.controllers;

import com.efcs.springboot.webnovel.dto.novel.NovelRequest;
import com.efcs.springboot.webnovel.dto.novel.NovelResponse;
import com.efcs.springboot.webnovel.entities.Novel;
import com.efcs.springboot.webnovel.service.NovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("novels")
@CrossOrigin({"http://localhost:8080"})
public class NovelController {

    private final NovelService service;


    public NovelController(NovelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NovelResponse>> list(){
        List<NovelResponse> novels = service.findAll()
                .stream()
                .map( novel -> new NovelResponse(
                        novel.getId(),
                        novel.getTitle(),
                        novel.getDescription(),
                        novel.getCoverImage()
                ))
                .toList();
        return ResponseEntity.ok(novels);
    }

    @GetMapping("/usr")
    public ResponseEntity<List<NovelResponse>> listNovels(){
        List<NovelResponse> novels = service.findByUser()
                .stream()
                .map( novel -> new NovelResponse(
                        novel.getId(), novel.getTitle(),
                        novel.getDescription(),
                        novel.getCoverImage()
                ))
                .toList();
        return ResponseEntity.ok(novels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NovelResponse> details(@PathVariable Long id){
        Optional<Novel> optionalNovel = service.findById(id);
        if (optionalNovel.isPresent()){
            Novel novel = optionalNovel.orElseThrow();
            NovelResponse novelResponse = new NovelResponse(
                    novel.getId(), novel.getTitle(),
                    novel.getDescription(),
                    novel.getCoverImage()
            );
            return  ResponseEntity.ok(novelResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NovelResponse> create(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        Novel novel = new Novel();
        novel.setTitle(title);
        novel.setDescription(description);

        Novel savedNovel = service.save(novel,imagen);
        NovelResponse response = new NovelResponse(
                novel.getId(), savedNovel.getTitle(),
                savedNovel.getDescription(),
                savedNovel.getCoverImage()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NovelResponse> update(@RequestBody NovelRequest novelRequest, @PathVariable Long id){
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NovelResponse> delete(@PathVariable Long id){
        Optional<Novel> novelOptional = service.deleteById(id);
        if (novelOptional.isPresent()){
            Novel novelDeleted = novelOptional.orElseThrow();
            NovelResponse novelResponse = new NovelResponse(
                    novelDeleted.getId(), novelDeleted.getTitle(),
                    novelDeleted.getDescription(),
                    novelDeleted.getCoverImage()
            );
            return ResponseEntity.status(HttpStatus.OK).body(novelResponse);
        }

        return ResponseEntity.notFound().build();

    }
}
