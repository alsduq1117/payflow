package com.payflow.payflow.client;

import com.payflow.payflow.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "tossClient", url = "${PSP.toss.url}")
public interface TossFeignClient {

    @PostMapping(value = "/v1/payments/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    String confirm(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> request
    );
}
