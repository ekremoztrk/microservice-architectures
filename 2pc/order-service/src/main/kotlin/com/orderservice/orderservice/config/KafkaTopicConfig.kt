package com.orderservice.orderservice.config

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    fun userRegistered(): NewTopic? {
        return TopicBuilder.name("order-placed").build()
    }

}