package com.orchestrator.orchestrator.model

import com.orchestrator.orchestrator.entity.Status

data class DeliveryEvent(

    val orderId: Long,

    val status: Status
)
