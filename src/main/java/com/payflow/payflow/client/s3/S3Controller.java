package com.payflow.payflow.client.s3;

import com.payflow.payflow.dto.s3.PresignedUrlRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
@Slf4j
public class S3Controller {

    private final S3PresignService s3PresignService;

    @PostMapping("/presigned-url")
    public ResponseEntity<String> generatePresignedUrl(@Valid @RequestBody PresignedUrlRequest request) {
        String url = s3PresignService.generatePresignedUrl(request);
        return ResponseEntity.ok(url);
    }
}
