package com.payflow.payflow.client;

import com.payflow.payflow.domain.PaymentStatus;
import lombok.Getter;

@Getter
public class PSPConfirmationException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final boolean isSuccess;
    private final boolean isFailure;
    private final boolean isUnknown;
    private final boolean isRetryableError;

    public PSPConfirmationException(String errorCode, String errorMessage, boolean isSuccess, boolean isFailure, boolean isUnknown, boolean isRetryableError) {
        this(errorCode, errorMessage, isSuccess, isFailure, isUnknown, isRetryableError, null);
    }

    public PSPConfirmationException(String errorCode, String errorMessage, boolean isSuccess, boolean isFailure, boolean isUnknown, boolean isRetryableError, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.isSuccess = isSuccess;
        this.isFailure = isFailure;
        this.isUnknown = isUnknown;
        this.isRetryableError = isRetryableError;

        if (!(isSuccess || isFailure || isUnknown)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " 는 올바르지 않은 결제 상태를 가지고 있습니다.");
        }
    }
}
