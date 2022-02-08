package com.orchestrator.orchestrator.model

import java.math.BigDecimal

data class OrderRequest(

    var orderId: Long? = null,

    val accountId: Long,

    val productId: Long,

    val amountOfProduct: Int,

    val productUnitPrice: BigDecimal

)
