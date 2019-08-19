package com.changan.consumerdemo.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "microservice-provider-user", configuration = AnnotationRibbonClientConfiguration.class)
public class AnnotationRibbonClient {

}