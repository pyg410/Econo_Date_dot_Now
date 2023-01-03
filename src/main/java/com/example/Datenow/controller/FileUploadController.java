package com.example.Datenow.controller;

import com.example.Datenow.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final S3Upload s3Upload;

    // MultipartFile 타입을 사용해서 클라이언트로부터 파일을 받아온다.
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity(s3Upload.upload(multipartFile), HttpStatus.CREATED);
    }
}