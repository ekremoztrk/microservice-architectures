package com.orchestrator.orchestrator.messageBus

import com.fasterxml.jackson.databind.ObjectMapper
import com.orchestrator.orchestrator.entity.Status
import com.orchestrator.orchestrator.model.OrderSagaReplyModel
import com.orchestrator.orchestrator.service.CommandService
import com.orchestrator.orchestrator.service.Orchestrator
import com.orchestrator.orchestrator.service.TransactionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val orchestrator: Orchestrator,
) {

    private val log: Logger = LoggerFactory.getLogger(Producer::class.java)

    @KafkaListener(topics = ["order-saga-reply"], groupId = "Saga-Orchestrator-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: OrderSagaReplyModel = mapper.readValue(message, OrderSagaReplyModel::class.java)

        try {
            orchestrator.eventReply(event)
        }catch (e: Exception){
            log.info("error: ${e.message}")
        }
    }


    /*
    @KafkaListener(topics = ["stock-executed"], groupId = "Saga-Orchestrator-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: StockEvent = mapper.readValue(message, StockEvent::class.java)

        orchestrator.stockExecuted(stockEvent = event)
    }

    @KafkaListener(topics = ["payment-executed"], groupId = "Saga-Orchestrator-Service")
    fun paymentExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: PaymentEvent = mapper.readValue(message, PaymentEvent::class.java)
        orchestrator.paymentExecuted(paymentEvent = event)
    }

    @KafkaListener(topics = ["delivery-executed"], groupId = "Saga-Orchestrator-Service")
    fun deliveryExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: DeliveryEvent = mapper.readValue(message, DeliveryEvent::class.java)
        orchestrator.deliveryExecuted(event)
    }

     */
}