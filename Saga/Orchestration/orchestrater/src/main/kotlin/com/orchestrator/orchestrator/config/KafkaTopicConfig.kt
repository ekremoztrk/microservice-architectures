package com.orchestrator.orchestrator.config

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    fun executePayment(): NewTopic? {
        return TopicBuilder.name("execute-payment").build()
    }

    @Bean
    fun paymentExecuted(): NewTopic? {
        return TopicBuilder.name("payment-executed").build()
    }

    @Bean
    fun executeStock(): NewTopic? {
        return TopicBuilder.name("execute-stock").build()
    }

    @Bean
    fun stockExecuted(): NewTopic? {
        return TopicBuilder.name("stock-executed").build()
    }

    @Bean
    fun executeDelivery(): NewTopic? {
        return TopicBuilder.name("execute-delivery").build()
    }

    @Bean
    fun deliveryExecuted(): NewTopic? {
        return TopicBuilder.name("delivery-executed").build()
    }

}