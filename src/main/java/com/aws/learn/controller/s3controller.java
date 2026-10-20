package com.aws.learn.controller;

import java.io.IOException;

import com.aws.learn.service.S3Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/s3")
public class s3controller {

    private final S3Service s3Service;

    public s3controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDataS3(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        return ResponseEntity.ok(
                s3Service.uploadDataS3(file)
        );
    }

    @GetMapping("/download/{key}")
    public ResponseEntity<byte[]> getDataS3(
            @PathVariable String key) throws IOException {

        try (ResponseInputStream<GetObjectResponse> input =
                     s3Service.getDataS3(key)) {

            byte[] data = input.readAllBytes();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + key + "\""
                    )
                    .body(data);
        }
    }
}