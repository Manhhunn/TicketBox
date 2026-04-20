package com.truongduchoang.SpringBootRESTfullAPIs.services;

import org.springframework.web.multipart.MultipartFile;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.MediaUploadResponse;

public interface CloudinaryService {
    MediaUploadResponse uploadImage(MultipartFile file, String folder);
}
