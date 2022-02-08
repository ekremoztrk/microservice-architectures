package com.backend.stockService.config

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {


    @Bean
    fun executeDelivery(): NewTopic? {
        return TopicBuilder.name("execute-delivery").build()
    }

    @Bean
    fun deliveryExecuted(): NewTopic? {
        return TopicBuilder.name("delivery-executed").build()
    }

}