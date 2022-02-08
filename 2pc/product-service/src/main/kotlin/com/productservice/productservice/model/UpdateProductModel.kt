package com.productservice.productservice.model

import java.math.BigDecimal

data class UpdateProductModel(
    val productId: Long,
    val amount: Int,
    val price: BigDecimal,
    override var status: Status,
    override var rollbackName: String

): EventModel(status, rollbackName)
