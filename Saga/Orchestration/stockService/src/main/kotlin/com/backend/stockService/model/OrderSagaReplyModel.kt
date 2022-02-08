package com.backend.stockService.model

data class OrderSagaReplyModel(

    val orderId: Long,

    val status: Status,

    val transactionType: TransactionType
)
