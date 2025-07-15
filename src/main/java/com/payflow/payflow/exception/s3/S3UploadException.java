package com.payflow.payflow.exception.s3;

import com.payflow.payflow.exception.CustomException;
import org.springframework.http.HttpStatus;

public class S3UploadException extends CustomException {

    private static final String MESSAGE = "S3 업로드 중 에러가 발생";

    public S3UploadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
