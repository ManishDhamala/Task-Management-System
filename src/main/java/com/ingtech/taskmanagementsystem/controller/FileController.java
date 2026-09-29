package com.ingtech.taskmanagementsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        String filePath = uploadDir + File.separator + file.getOriginalFilename();
        String fileUploadStatus;

        try {
            Files.createDirectories(Paths.get(uploadDir));

            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(file.getBytes());

            fos.close();
            fileUploadStatus = "File uploaded successfully";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fileUploadStatus = "Error in handling file: " + e.getMessage();
        }

        return fileUploadStatus;
    }

    @GetMapping("/getFiles")
    public String[] getFiles() {
        String folderPath = System.getProperty("user.dir") + File.separator + "uploads";

        File directory = new File(folderPath);
        String[] fileNames = directory.list();
        return fileNames;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) throws FileNotFoundException {
        String folderPath = System.getProperty("user.dir") + File.separator + "uploads";
        String[] filesName = this.getFiles();
        boolean contains = Arrays.asList(filesName).contains(fileName);
        if(!contains){
            return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
        }

        String filePath = folderPath + File.separator + fileName;
        File file = new File(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        String headerValue = "attachment; filename=\""+resource.getFilename()+"\"";
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(resource);
    }

}
