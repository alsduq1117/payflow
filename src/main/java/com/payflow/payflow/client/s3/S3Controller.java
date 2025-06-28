package com.payflow.payflow.client.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class S3Controller {

    private final S3Service s3Service;

    /**
     * S3에 파일 업로드 후, 해당 파일의 접근 URL 반환
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        String uploadedUrl = s3Service.uploadFile(file, path);
        return ResponseEntity.ok(uploadedUrl);
    }
}
