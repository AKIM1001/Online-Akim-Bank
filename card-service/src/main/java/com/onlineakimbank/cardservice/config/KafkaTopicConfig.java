package com.onlineakimbank.cardservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic cardRegistrationTopic() {
        return TopicBuilder.name("card-registration")
                .build();
    }

    @Bean
    public NewTopic cardStatusChangeTopic() {
        return TopicBuilder.name("card-status-change")
                .build();
    }

    @Bean
    public NewTopic cardRemoveTopic() {
        return TopicBuilder.name("card-remove")
                .build();
    }

    @Bean
    public NewTopic cardRegisteredTopic() {
        return TopicBuilder.name("card_registered")
                .build();
    }
}
