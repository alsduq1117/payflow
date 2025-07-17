package com.payflow.payflow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@RequiredArgsConstructor
public class RecoveryExecutorConfig {

    private final RecoveryProperties properties;

    @Bean("recoveryExecutor")
    public Executor recoveryExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getThreads());
        executor.setMaxPoolSize(properties.getThreads());
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("recovery-");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();
        return executor;
    }

}
