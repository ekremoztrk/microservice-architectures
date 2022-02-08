package com.backend.deliveryService.service

import com.backend.deliveryService.messageBus.Producer
import com.backend.deliveryService.model.DeliveryEvent
import com.backend.deliveryService.model.Status
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    private val producer: Producer
) {

    fun processOrderDeliveryRequest(deliveryEvent: DeliveryEvent) {
        if(deliveryEvent.status != Status.ROLLBACK){
            producer.sendExecuteDelivery(
                DeliveryEvent(orderId = deliveryEvent.orderId,
                    status = Status.PROCESSED)
            )
        }
    }
}