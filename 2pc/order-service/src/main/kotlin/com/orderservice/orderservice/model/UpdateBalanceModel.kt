package com.orderservice.orderservice.model


data class UpdateBalanceModel(
    val accountId: Long,
    val balance: Int,
    override val status: Status,
    override val rollbackName: String

    ): EventModel(status, rollbackName)