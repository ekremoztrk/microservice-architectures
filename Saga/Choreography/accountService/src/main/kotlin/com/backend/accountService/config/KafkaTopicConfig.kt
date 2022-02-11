package com.backend.accountService.config

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {


    @Bean
    fun executePayment(): NewTopic? {
        return TopicBuilder.name("order-create-event").build()
    }

    @Bean
    fun paymentExecuted(): NewTopic? {
        return TopicBuilder.name("order-billed-event").build()
    }

}