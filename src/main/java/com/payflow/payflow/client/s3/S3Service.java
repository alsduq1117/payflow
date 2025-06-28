package com.payflow.payflow.client.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.payflow.payflow.exception.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 업로드 (UUID 미적용)
    public String uploadFile(MultipartFile multipartFile, String path) {
        String fileName = path + "/" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getSize());

        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new S3UploadException();
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

}