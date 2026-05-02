package com.onlineakimbank.accountservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic accountRegistrationTopic() {
        return TopicBuilder.name("account-registration")
                .build();
    }

    @Bean
    public NewTopic accountStateChangeTopic() {
        return TopicBuilder.name("account-state-change")
                .build();
    }

    @Bean
    public NewTopic accountStatusChangeTopic() {
        return TopicBuilder.name("account-status-change")
                .build();
    }

    @Bean
    public NewTopic accountBalanceChangedTopic() {
        return TopicBuilder.name("account-balance-change")
                .build();
    }

    @Bean
    public NewTopic userRegisteredTopic() {
        return TopicBuilder.name("user-registered")
                .build();
    }
}
