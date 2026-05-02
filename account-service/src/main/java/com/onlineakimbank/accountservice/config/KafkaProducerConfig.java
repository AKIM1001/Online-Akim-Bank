package com.onlineakimbank.accountservice.config;

import com.onlineakimbank.accountservice.event.account.AccountRegistrationEvent;
import com.onlineakimbank.accountservice.event.account.AccountRemoveEvent;
import com.onlineakimbank.accountservice.event.account.AccountStateChangeEvent;
import com.onlineakimbank.accountservice.event.account.AccountStatusChangeEvent;
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
    public ProducerFactory<String, AccountRegistrationEvent> accountRegistrationEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, AccountRegistrationEvent> accountRegistrationEventKafkaTemplate(
            ProducerFactory<String, AccountRegistrationEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, AccountStateChangeEvent> accountStateChangeEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, AccountStateChangeEvent> accountStateChangeEventKafkaTemplate(
            ProducerFactory<String, AccountStateChangeEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, AccountStatusChangeEvent> accountStatusChangeEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, AccountStatusChangeEvent> accountStatusChangeEventKafkaTemplate(
            ProducerFactory<String, AccountStatusChangeEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, AccountRemoveEvent> accountRemoveEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, AccountRemoveEvent> accountRemoveEventKafkaTemplate(
            ProducerFactory<String, AccountRemoveEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
