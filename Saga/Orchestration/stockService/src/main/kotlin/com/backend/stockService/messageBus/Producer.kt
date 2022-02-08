package com.backend.stockService.messageBus

import com.backend.stockService.model.OrderSagaReplyModel
import com.backend.stockService.model.StockEvent
import com.backend.stockService.model.TransactionType
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


    fun sendExecuteStock(stockEvent: StockEvent) {
        val orderSaga = OrderSagaReplyModel(stockEvent.orderId, stockEvent.status, TransactionType.PRODUCT_TRANSACTION)
        log.info("sending message='{}' to topic='{}'", orderSaga, "order-saga-reply")

        kafkaTemplate!!.send("order-saga-reply", orderSaga)
    }
}