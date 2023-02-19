package com.nitesh.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerWithErrorHandlerConfig {

    private final ApplicationConfig applicationConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaConsumerWithErrorHandlerConfig(ApplicationConfig applicationConfig,
                                               KafkaTemplate<String, String> kafkaTemplate) {

        this.applicationConfig = applicationConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    public DefaultErrorHandler getErrorHandler() {
        ExponentialBackOffWithMaxRetries expBackOff = new ExponentialBackOffWithMaxRetries(2);
        expBackOff.setInitialInterval(1_000L);
        expBackOff.setMultiplier(2.0);
        expBackOff.setMaxInterval(2_000L);

//        BackOff fixedBackOff = new FixedBackOff(1000L, 2L);

        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate, (r, e) -> {
            return new TopicPartition(r.topic() + ".dlt", 1);
        }), expBackOff);
    }

    @Bean(name = "simpleKafkaConcurrentFactoryListenerWithRetry")
    public ConcurrentKafkaListenerContainerFactory<String, String> simpleKafkaConcurrentConsumerFactoryWithManualAck(
            @Qualifier("simpleConsumerFactory") ConsumerFactory<String, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);
        factory.setAutoStartup(applicationConfig.getKafkaConsumerAutoStartup());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        factory.setCommonErrorHandler(getErrorHandler());
        return factory;
    }
}
