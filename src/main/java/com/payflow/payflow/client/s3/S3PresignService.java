package com.payflow.payflow.client.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.payflow.payflow.dto.s3.PresignedUrlRequest;
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

    public String generatePresignedUrl(PresignedUrlRequest presignedUrlRequest) {
        String fullPath = presignedUrlRequest.getFolder() + "/" + UUID.randomUUID() + "_" + presignedUrlRequest.getFileName();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5);

        GeneratePresignedUrlRequest s3PresignedRequest = new GeneratePresignedUrlRequest(bucket, fullPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        s3PresignedRequest.setContentType(presignedUrlRequest.getContentType());

        URL url = amazonS3.generatePresignedUrl(s3PresignedRequest);
        return url.toString();
    }

}