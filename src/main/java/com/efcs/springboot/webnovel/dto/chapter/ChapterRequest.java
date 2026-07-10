package com.efcs.springboot.webnovel.dto.chapter;

public record ChapterRequest (
        String title,
        String content,
        Long novelId
){

}
