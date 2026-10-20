package com.aws.learn.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(
            S3Client s3Client,
            @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadDataS3(MultipartFile file)
            throws IOException {

        String key = file.getOriginalFilename();

        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Filename is required");
        }

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromInputStream(
                        file.getInputStream(),
                        file.getSize()
                )
        );

        return "File uploaded successfully: " + key;
    }

    public ResponseInputStream<GetObjectResponse> getDataS3(
            String key) {

        return s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );
    }
}
