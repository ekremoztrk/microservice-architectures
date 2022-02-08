package com.orderservice.orderservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.List

@Configuration
@EnableKafka
class KafkaConfig {

    @Bean
    fun producerFactory(): ProducerFactory<Any, Any> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, String?> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "2pc-example"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun concurrentKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<*, *>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<*, *>? {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun createTemplate(properties: KafkaProperties): KafkaTemplate<Int, String>? {
        properties.bootstrapServers = List.of("localhost:9092")
        val props = properties.buildProducerProperties()
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return KafkaTemplate(DefaultKafkaProducerFactory(props))
    }
}