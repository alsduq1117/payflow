package com.payflow.payflow.config;

import com.payflow.payflow.client.PSPConfirmationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .exponentialBackoff(1000, 2, 5000, true)
                .retryOn(e -> (e instanceof PSPConfirmationException pspConfirmationException) && pspConfirmationException.isRetryableError())
                .build();
    }
}
