package com.efcs.springboot.webnovel.dto.novel;

public record NovelResponse (
        Long id, String title,
        String description,
        String coverImage
){
}
