package com.backend.accountService.model

import java.math.BigDecimal

data class OrderEvent(
    val accountId: Long,
    val productId: Long,
    val amountOfProduct: Int,
    val productUnitPrice: BigDecimal
)
