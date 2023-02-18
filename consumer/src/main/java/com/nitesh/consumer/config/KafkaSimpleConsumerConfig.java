package com.nitesh.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaSimpleConsumerConfig {

    private final ApplicationConfig applicationConfig;

    public KafkaSimpleConsumerConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    private Map<String, Object> simpleConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.getKafkaBootStrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }

    @Bean(name = "simpleConsumerFactory")
    public ConsumerFactory<String, Object> simpleConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(simpleConsumerConfig());
    }

    @Bean(name = "simpleKafkaConcurrentFactoryListener")
    public ConcurrentKafkaListenerContainerFactory<String, String> simpleKafkaConcurrentConsumerFactory(
            @Qualifier("simpleConsumerFactory") ConsumerFactory<String, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);
        factory.setAutoStartup(applicationConfig.getKafkaConsumerAutoStartup());

        return factory;
    }

    @Bean(name = "simpleKafkaConcurrentFactoryListenerWithManualAck")
    public ConcurrentKafkaListenerContainerFactory<String, String> simpleKafkaConcurrentConsumerFactoryWithManualAck(
            @Qualifier("simpleConsumerFactory") ConsumerFactory<String, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);
        factory.setAutoStartup(applicationConfig.getKafkaConsumerAutoStartup());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
