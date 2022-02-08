package com.backend.deliveryService.model

data class OrderSagaReplyModel(

    val orderId: Long,

    val status: Status,

    val transactionType: TransactionType
)
