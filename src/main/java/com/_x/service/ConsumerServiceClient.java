package com._x.service;

import com._x.dto.CandidateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "consumer")  // This name must match the service name in Eureka
public interface ConsumerServiceClient {

    @PostMapping("/api/v1/consumer")  // The endpoint in the consumer microservice that will receive data
    void sendCandidateData(@RequestBody CandidateDTO candidate);
}