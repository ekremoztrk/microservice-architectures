package com.backend.accountService.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PaymentEvent(

    @JsonProperty("orderId")
    val orderId: Long,
    @JsonProperty("accountId")
    val accountId: Long = -1,
    @JsonProperty("price")
    val price: BigDecimal = BigDecimal.ZERO,
    @JsonProperty("status")
    val status: Status
)
