package com.changan.eurekademo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
//@ConfigurationProperties("spring.kafka")
public class KafkaConfig {

    @Value("bootstrap-servers")
    private String bootstrapServers;

    @Value("producer")
    private Object producer;

    @Value("consumer")
    private Object consumer;

    //@Bean
    public KafkaTemplate getKafkaTemplate(){
        Map<String,Object> config = new HashMap<>();
        config.put("bootstrap-servers",bootstrapServers);
        config.put("producer",producer);
        config.put("consumer",consumer);

        DefaultKafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory(config);

        return new KafkaTemplate(producerFactory);
    }
}
