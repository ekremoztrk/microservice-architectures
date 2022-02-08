package com.backend.accountService.messageBus

import com.backend.accountService.model.OrderSagaReplyModel
import com.backend.accountService.model.PaymentEvent
import com.backend.accountService.model.TransactionType
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


    fun sendExecutePayment(paymentEvent: PaymentEvent) {
        val orderSaga = OrderSagaReplyModel(paymentEvent.orderId, paymentEvent.status, TransactionType.PAYMENT_TRANSACTION)
        log.info("sending message='{}' to topic='{}'", orderSaga, "payment-executed")

        kafkaTemplate!!.send("order-saga-reply", orderSaga)
    }
}