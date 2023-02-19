package com.nitesh.consumer.config;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaSimpleProducerConfig {
    private final ApplicationConfig applicationConfig;

    public KafkaSimpleProducerConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean(name = "simpleProducer2")
    public ProducerFactory<String, String> simpleProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.getKafkaBootStrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);

    }

    @Bean(name = "simpleKafkaTemplate2")
    public KafkaTemplate<String, String> getSimpleKafkaTemplate() {
        KafkaTemplate<String, String> template =  new KafkaTemplate<>(simpleProducerFactory());

        return template;
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
