package com.backend.accountService.config

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {


    @Bean
    fun executeStock(): NewTopic? {
        return TopicBuilder.name("execute-payment").build()
    }

    @Bean
    fun stockExecuted(): NewTopic? {
        return TopicBuilder.name("c").build()
    }

}