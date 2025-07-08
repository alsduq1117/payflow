package com.payflow.payflow.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TossFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            TossPaymentConfirmationResponse.TossFailureResponse failure = objectMapper.readValue(response.body().asInputStream(), TossPaymentConfirmationResponse.TossFailureResponse.class);
            TossPaymentError error = TossPaymentError.get(failure.getCode());
            return new PSPConfirmationException(
                    error.name(),
                    error.getDescription(),
                    error.isSuccess(),
                    error.isFailure(),
                    error.isUnknown(),
                    error.isRetryableError()
            );
        } catch (IOException e) {
            return new RuntimeException("응답 디코딩 중 오류 발생", e);
        }
    }
}
