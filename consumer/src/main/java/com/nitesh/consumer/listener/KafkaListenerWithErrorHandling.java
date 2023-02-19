package com.nitesh.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerWithErrorHandling {

    @KafkaListener(
            topics = "simple.test.topic.error",
            containerFactory = "simpleKafkaConcurrentFactoryListenerWithRetry",
            autoStartup = "true",
            id = "simple-kafka-consumer-group-manual-ack-error"
    )
    public void processRecordWithAck(@Payload ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        Object value = consumerRecord.value();
        try {
            int val = Integer.parseInt((String) value);
            log.info("Val is {}", val);
            if (val > 100) {
                throw new RuntimeException("Don't know");
            }
            acknowledgment.acknowledge();
        } catch (NumberFormatException ex) {
            log.info("Number format exception ");
            acknowledgment.acknowledge();
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
