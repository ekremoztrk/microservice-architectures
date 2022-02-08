package com.orchestrator.orchestrator.model

import com.orchestrator.orchestrator.entity.Status
import java.math.BigDecimal

data class PaymentEvent(

    val orderId: Long,

    val accountId: Long = -1,

    val price: BigDecimal = BigDecimal.ZERO,

    val status: Status
)
