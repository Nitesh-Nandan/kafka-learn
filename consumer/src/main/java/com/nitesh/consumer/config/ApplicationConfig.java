package com.nitesh.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootStrapServers;

    @Value("${kafka.max.pool.message}")
    private Integer kafkaMaxPoolMessage;

//    @Value("kafka.consumer.auto.startup")
    private Boolean kafkaConsumerAutoStartup = true;
}
