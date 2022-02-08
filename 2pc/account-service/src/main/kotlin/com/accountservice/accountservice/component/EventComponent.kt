package com.accountservice.accountservice.component

import com.accountservice.accountservice.model.EventModel
import com.accountservice.accountservice.model.Status
import com.accountservice.accountservice.model.UpdateBalanceModel
import com.accountservice.accountservice.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
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
    val accountService:  AccountService
) {

    private val log: Logger = LoggerFactory.getLogger(EventComponent::class.java)

    @KafkaListener(topics = ["order-placed"], groupId = "2pc-account-service")
    fun saveUser(message: String?) {
        println(message)
        val mapper = ObjectMapper()
        val event: EventModel = mapper.readValue(message, EventModel::class.java)
        if(event.status == Status.FAILED){
            accountService.backToSave(event.rollbackName)
            accountService.commitConnection()
        }
        if(event.status == Status.SUCCESS)
            accountService.commitConnection()
    }

}