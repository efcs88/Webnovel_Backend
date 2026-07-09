package com.efcs.springboot.webnovel.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService{

    private final Path rootLocation = Paths.get("uploads/covers");

    @Override
    public String save(MultipartFile file,String title){

        String titleMod = title
                .trim()
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+","-");

        if (file == null || file.isEmpty()){
            throw new RuntimeException("The file is empty");
        }
        try {
            Files.createDirectories(rootLocation);
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = "";

            int index = originalFileName.lastIndexOf(".");
            if (index != -1) {
                extension = originalFileName.substring(index);
            }
            String fileName = titleMod + "-" + UUID.randomUUID() + extension;

            Path destination = rootLocation.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return fileName;
        }catch (IOException e){
            throw new RuntimeException(("The image could not be saved"));
        }
    }
}
