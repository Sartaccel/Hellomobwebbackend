package com.hellomobiles.dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    public String uploadFile(MultipartFile file) {

        // Here you will upload file to AWS S3
        // For now we return a sample URL

        return "https://s3.amazonaws.com/bucket/" + file.getOriginalFilename();
    }
}