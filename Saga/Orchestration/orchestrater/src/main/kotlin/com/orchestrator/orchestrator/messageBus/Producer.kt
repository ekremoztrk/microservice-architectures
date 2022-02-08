package com.orchestrator.orchestrator.messageBus

import com.orchestrator.orchestrator.model.DeliveryEvent
import com.orchestrator.orchestrator.model.PaymentEvent
import com.orchestrator.orchestrator.model.StockEvent
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


    fun sendExecutePayment(payment: PaymentEvent) {
        log.info("sending message='{}' to topic='{}'", payment, "execute-payment")
        kafkaTemplate!!.send("execute-payment", payment)
    }

    fun sendExecuteStock(stockEvent: StockEvent) {
        log.info("sending message='{}' to topic='{}'", stockEvent, "execute-stock")
        kafkaTemplate!!.send("execute-stock", stockEvent)
    }

    fun sendExecuteDelivery(delivery: DeliveryEvent) {
        log.info("sending message='{}' to topic='{}'", delivery, "execute-delivery")
        kafkaTemplate!!.send("execute-delivery", delivery)
    }
}