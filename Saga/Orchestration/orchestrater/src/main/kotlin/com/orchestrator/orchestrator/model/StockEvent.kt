package com.orchestrator.orchestrator.model

import com.orchestrator.orchestrator.entity.Status
import java.math.BigDecimal

data class StockEvent(

    val orderId: Long,

    val productId: Long,

    val amount: Int,

    val status: Status
)
