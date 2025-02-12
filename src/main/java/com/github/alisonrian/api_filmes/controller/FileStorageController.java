package com.github.alisonrian.api_filmes.controller;

import com.github.alisonrian.api_filmes.config.FileStorageProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@CrossOrigin(origins = "*")
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
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("images/download/")
                    .path(fileName)
                    .toUriString();
            return ResponseEntity.ok(fileDownloadUri);
        }catch (IOException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        try{
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if(contentType != null){
                contentType = "application/octet-stream";
            }
            return  ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\""+resource.getFile()+ "\"")
                    .body(resource);
        }catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }

}
