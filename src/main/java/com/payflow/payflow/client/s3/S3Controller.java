package com.payflow.payflow.client.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
@Slf4j
public class S3Controller {

    private final S3PresignService s3PresignService;

    @PostMapping("/presigned-url")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName, @RequestParam String contentType, @RequestParam String folder) {
        String url = s3PresignService.generatePresignedUrl(fileName, contentType, folder);
        return ResponseEntity.ok(url);
    }
}
