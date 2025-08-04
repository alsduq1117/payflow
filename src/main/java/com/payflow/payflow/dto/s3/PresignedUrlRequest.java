package com.payflow.payflow.dto.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PresignedUrlRequest {

    private String fileName;
    private String contentType;
    private String folder;

    @Builder
    public PresignedUrlRequest(String fileName, String contentType, String folder) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.folder = folder;
    }
}
