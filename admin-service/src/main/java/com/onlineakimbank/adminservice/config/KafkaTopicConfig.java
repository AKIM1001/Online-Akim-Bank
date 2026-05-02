package com.onlineakimbank.adminservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic changeAccountStateTopic() {
        return TopicBuilder.name("change-account-state")
                .build();
    }

    @Bean
    public NewTopic changeCardStatusTopic() {
        return TopicBuilder.name("change-account-status")
                .build();
    }

    @Bean
    public NewTopic loanDecisionTopic() {
        return TopicBuilder.name("loan-decision")
                .build();
    }

    @Bean
    public NewTopic loanProductTopic() {
        return TopicBuilder.name("loan-product")
                .build();
    }

}
