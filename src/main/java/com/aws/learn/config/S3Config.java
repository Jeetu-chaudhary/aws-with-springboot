package com.aws.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
String accesskey="";
    String secreatekey="";
    @Bean
    public S3Client s3Client(
            @Value("${aws.region}") String region) {
        AwsBasicCredentials credentialsProvider = AwsBasicCredentials.create(accesskey,secreatekey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentialsProvider))
                .build();
    }
}
