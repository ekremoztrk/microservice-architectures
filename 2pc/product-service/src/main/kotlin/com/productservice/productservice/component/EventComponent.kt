package com.productservice.productservice.component


import com.fasterxml.jackson.databind.ObjectMapper
import com.productservice.productservice.model.EventModel
import com.productservice.productservice.model.Status
import com.productservice.productservice.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class EventComponent(
    @Lazy
    val productService: ProductService
) {

    private val log: Logger = LoggerFactory.getLogger(EventComponent::class.java)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any>? = null


    @KafkaListener(topics = ["order-placed"], groupId = "2pc-product-service")
    fun saveUser(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: EventModel = mapper.readValue(message, EventModel::class.java)
        if(event.status == Status.FAILED){
            productService.backToSave(event.rollbackName)
            productService.commitConnection()
        }
        if(event.status == Status.SUCCESS)
            productService.commitConnection()
    }

}