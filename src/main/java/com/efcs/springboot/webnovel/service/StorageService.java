package com.efcs.springboot.webnovel.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String save(MultipartFile file,String title);
}
