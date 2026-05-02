package com.onlineakimbank.cardservice.config;

import com.onlineakimbank.cardservice.event.card.CardRegistrationEvent;
import com.onlineakimbank.cardservice.event.card.CardRemoveEvent;
import com.onlineakimbank.cardservice.event.card.CardStatusChangeEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        HashMap<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    @Bean
    public ProducerFactory<String, CardRegistrationEvent> cardRegistrationEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CardRegistrationEvent> cardRegistrationEventKafkaTemplate(
            ProducerFactory<String, CardRegistrationEvent> producerFactory) {
                return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, CardStatusChangeEvent> cardStatusChangeEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CardStatusChangeEvent> cardStatusChangeEventKafkaTemplate(
            ProducerFactory<String, CardStatusChangeEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, CardRemoveEvent> cardRemoveEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CardRemoveEvent> cardRemoveEventKafkaTemplate(
            ProducerFactory<String, CardRemoveEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
