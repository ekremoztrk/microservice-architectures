package com.backend.deliveryService.messageBus

import com.backend.deliveryService.model.DeliveryEvent
import com.backend.deliveryService.model.Status
import com.backend.deliveryService.service.DeliveryService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val deliveryService: DeliveryService,
    private val producer: Producer
) {

    @KafkaListener(topics = ["execute-delivery"], groupId = "Saga-Orchestrator-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: DeliveryEvent = mapper.readValue(message, DeliveryEvent::class.java)

        try {
            deliveryService.processOrderDeliveryRequest(event)
        }catch (e: Exception){
            producer.sendExecuteDelivery(
                DeliveryEvent(orderId = event.orderId,
                status = Status.ROLLBACK)
            )
        }



    }

}