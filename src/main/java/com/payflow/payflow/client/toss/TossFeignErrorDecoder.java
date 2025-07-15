package com.payflow.payflow.client.toss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.client.PSPConfirmationException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TossFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8)); // stream-safe
            TossPaymentConfirmationResponse.TossFailureResponse failure = objectMapper.readValue(body, TossPaymentConfirmationResponse.TossFailureResponse.class);
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
