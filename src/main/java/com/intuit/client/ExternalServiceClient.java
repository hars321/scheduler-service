package com.intuit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "externalServiceClient", url = "${external.service.url}")
public interface ExternalServiceClient {

    @PostMapping("/api/notify")
    String sendNotification(@RequestParam("param") String param, @RequestBody String requestBody);
}
