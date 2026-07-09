package com.efcs.springboot.webnovel.dto.novel;

public record NovelRequest(
        Long id,
        String title,
        String description,
        String coverImage,
        String user_id
) {

}
