package com.nitesh.consumer.producer;

import com.github.javafaker.Faker;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleProducer {
    private static final String topic = "simple-kafka-topic-auto";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Faker faker;

    public SimpleProducer(KafkaTemplate<String, String> kafkaTemplate, Faker faker) {
        this.kafkaTemplate = kafkaTemplate;
        this.faker = faker;
    }

//    @Scheduled(cron = "*/10 * * * * *")
    public void publish() {
        int val = faker.number().numberBetween(10, 100);
        System.out.println(val);
        kafkaTemplate.send(topic, String.valueOf(val));
    }

    public void publishDummyMessage(String topic) {
        kafkaTemplate.send(topic, faker.name().fullName());
    }

    public void publishMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
