package com.backend.accountService.messageBus

import com.backend.accountService.model.OrderEvent
import com.backend.accountService.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val accountService: AccountService
) {

    @KafkaListener(topics = ["order-create-event"], groupId = "Saga-Choreography-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: OrderEvent = mapper.readValue(message, OrderEvent::class.java)
        accountService.processOrderPaymentRequest(event)

    }
}