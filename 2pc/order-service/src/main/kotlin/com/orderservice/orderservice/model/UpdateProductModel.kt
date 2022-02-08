package com.orderservice.orderservice.model

import java.math.BigDecimal

data class UpdateProductModel(
    val productId: Long,
    val amount: Int,
    val price: BigDecimal,
    override val status: Status,
    override val rollbackName: String

): EventModel(status, rollbackName)
