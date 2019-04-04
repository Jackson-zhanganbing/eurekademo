package com.changan.provider1demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Provider1demoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Provider1demoApplication.class, args);
    }

}
