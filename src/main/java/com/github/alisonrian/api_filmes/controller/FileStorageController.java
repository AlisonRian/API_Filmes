package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.config.FileStorageProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class FileStorageController {
    private final Path fileStorageLocation;
    public FileStorageController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);
            return ResponseEntity.ok().body(targetLocation.toString());
        }catch (IOException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
