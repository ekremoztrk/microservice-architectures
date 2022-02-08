package com.backend.accountService.messageBus

import com.backend.accountService.model.PaymentEvent
import com.backend.accountService.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val accountService: AccountService
) {

    @KafkaListener(topics = ["execute-payment"], groupId = "Saga-Orchestrator-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: PaymentEvent = mapper.readValue(message, PaymentEvent::class.java)
        accountService.processOrderPaymentRequest(event)

    }
}