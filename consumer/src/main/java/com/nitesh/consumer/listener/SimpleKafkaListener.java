package com.nitesh.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleKafkaListener {

    @KafkaListener(
            topics = "simple-kafka-topic",
            containerFactory = "simpleKafkaConcurrentFactoryListener",
            id = "simple-kafka-consumer-group"
    )
    public void processRecord(@Payload ConsumerRecord<String, String> consumerRecord) {
        Object value = consumerRecord.value();

        log.info("Message Received {}", value);
    }

    @KafkaListener(
            topics = "simple-kafka-topic",
            containerFactory = "simpleKafkaConcurrentFactoryListenerWithManualAck",
            id = "simple-kafka-consumer-group-manual-ack"
    )
    public void processRecordWithAck(@Payload ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        Object value = consumerRecord.value();

        log.info("Message Received With Manual Ack {}", value);
    }
}
