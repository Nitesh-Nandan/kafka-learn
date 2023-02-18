package com.nitesh.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@PropertySource("classpath:application-consumer.yaml")
@PropertySource("classpath:application-consumer.properties")
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
