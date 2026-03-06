package com.hellomobiles.dashboard.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private String accessKey = "YOUR_ACCESS_KEY";
    private String secretKey = "YOUR_SECRET_KEY";
    private String region = "ap-south-1";

    @Bean
    public AmazonS3 amazonS3() {

        BasicAWSCredentials credentials =
                new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}