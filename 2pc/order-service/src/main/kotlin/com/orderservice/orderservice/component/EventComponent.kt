package com.orderservice.orderservice.component


import com.orderservice.orderservice.model.EventModel
import com.orderservice.orderservice.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class EventComponent() {

    private val log: Logger = LoggerFactory.getLogger(EventComponent::class.java)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any>? = null


    fun sendProcessFinished(eventModel: EventModel) {
        log.info("sending message='{}' to topic='{}'", eventModel, "order-placed")
        kafkaTemplate!!.send("order-placed", eventModel)
    }


}