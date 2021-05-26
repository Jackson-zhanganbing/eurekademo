package com.changan.consumerdemo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("microservice-provider-user")
public interface TestService {

    @GetMapping("/api/user")
    String getUser();

    @GetMapping("/api/exception")
    String exception();

    @GetMapping("/api/timeout")
    String timeout();

}
