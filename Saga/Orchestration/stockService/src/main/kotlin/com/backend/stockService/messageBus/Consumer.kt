package com.backend.stockService.messageBus

import com.backend.stockService.model.Status
import com.backend.stockService.model.StockEvent
import com.backend.stockService.service.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val productService: ProductService,
    private val producer: Producer
) {

    @KafkaListener(topics = ["execute-stock"], groupId = "Saga-Orchestrator-Service")
    fun stockExecuted(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: StockEvent = mapper.readValue(message, StockEvent::class.java)

        try {
            productService.processOrderProductRequest(event)
        }catch (e: Exception){
            producer.sendExecuteStock(StockEvent(orderId = event.orderId,
                productId = event.productId,
                amount = event.amount,
                status = Status.ROLLBACK))
        }



    }

}