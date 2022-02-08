package com.orchestrator.orchestrator.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.orchestrator.orchestrator.entity.Status
import com.orchestrator.orchestrator.entity.TransactionType

data class OrderSagaReplyModel(

    @JsonProperty("orderId")
    val orderId: Long,
    @JsonProperty("status")
    val status: Status,
    @JsonProperty("transactionType")
    val transactionType: TransactionType
)
