package com.onlineakimbank.adminservice.config;

import com.onlineakimbank.adminservice.event.AccountStateChangeEvent;
import com.onlineakimbank.adminservice.event.CardStatusChangeEvent;
import com.onlineakimbank.adminservice.event.LoanDecisionEvent;
import com.onlineakimbank.adminservice.event.LoanProductEvent;
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
    public ProducerFactory<String, LoanDecisionEvent> loanDecisionEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, LoanDecisionEvent> loanDecisionEventKafkaTemplate(
            ProducerFactory<String, LoanDecisionEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, LoanProductEvent> loanProductEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, LoanProductEvent> loanProductEventKafkaTemplate(
            ProducerFactory<String, LoanProductEvent> producerFactory) {
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
    public ProducerFactory<String, CardStatusChangeEvent> cardStatusChangeEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CardStatusChangeEvent> cardStatusChangeEventKafkaTemplate(
            ProducerFactory<String, CardStatusChangeEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
