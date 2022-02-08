package com.backend.deliveryService.messageBus

import com.backend.deliveryService.model.DeliveryEvent
import com.backend.deliveryService.model.OrderSagaReplyModel
import com.backend.deliveryService.model.TransactionType
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Producer {

    private val log: Logger = LoggerFactory.getLogger(Producer::class.java)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any>? = null


    fun sendExecuteDelivery(deliveryEvent: DeliveryEvent) {
        val orderSaga = OrderSagaReplyModel(deliveryEvent.orderId, deliveryEvent.status, TransactionType.DELIVERY_TRANSACTION)
        log.info("sending message='{}' to topic='{}'", orderSaga, "order-saga-reply")
        val objectMapper = ObjectMapper()

        kafkaTemplate!!.send("order-saga-reply", objectMapper.writeValueAsString(orderSaga))
    }
}