package com.backend.accountService.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderSagaReplyModel(

    val orderId: Long,

    val status: Status,

    val transactionType: TransactionType
)
