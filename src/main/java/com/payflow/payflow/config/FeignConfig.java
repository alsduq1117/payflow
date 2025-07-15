package com.payflow.payflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.client.toss.TossFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.payflow")
public class FeignConfig {

    @Bean
    public ErrorDecoder tossFeignErrorDecoder(ObjectMapper objectMapper) {
        return new TossFeignErrorDecoder(objectMapper);
    }
}
