package com.payflow.payflow.client.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3PresignService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 업로드 (UUID 미적용)
    public String generatePresignedUrl(String fileName, String contentType, String folder) {
        String fullPath = folder + "/" + UUID.randomUUID() + "_" + fileName;

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5); // 5분 유효

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, fullPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        request.setContentType(contentType);

        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString(); // 프론트에서 이 URL로 파일 PUT 가능
    }

}