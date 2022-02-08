package com.orderservice.orderservice.model

data class OrderRequest(
    val accountId: Long,
    val productId: Long,
    val amountOfProduct: Int
)
