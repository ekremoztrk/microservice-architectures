package com.backend.stockService.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StockEvent(

    @JsonProperty("orderID")
    val orderId: Long,
    @JsonProperty("productId")
    val productId: Long,
    @JsonProperty("amount")
    val amount: Int,
    @JsonProperty("status")
    val status: Status
)
