package com.efcs.springboot.webnovel.dto.chapter;

public record ChapterRequest (
        Long id,
        String title,
        String content,
        String novel_id
){

}
