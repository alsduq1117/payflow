package com.payflow.payflow.config;

import com.payflow.payflow.client.PSPConfirmationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

    @Bean(name = "pspRetryTemplate")
    public RetryTemplate pspRetryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .exponentialBackoff(1000, 2, 5000, true)
                .retryOn(e -> (e instanceof PSPConfirmationException psp) && psp.isRetryableError())
                .build();
    }

    @Bean(name = "lockingRetryTemplate")
    public RetryTemplate lockingRetryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 2, 1000, true)
                .retryOn(e -> e instanceof ObjectOptimisticLockingFailureException)
                .build();
    }
}
