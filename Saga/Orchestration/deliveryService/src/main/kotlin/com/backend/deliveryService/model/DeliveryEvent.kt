package com.backend.deliveryService.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DeliveryEvent(

    @JsonProperty("orderId")
    val orderId: Long,

    @JsonProperty("status")
    val status: Status
)
