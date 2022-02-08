package com.accountservice.accountservice.model

import java.math.BigDecimal


data class UpdateBalanceModel(
    val accountId: Long,
    val balance: BigDecimal,
    override var status: Status,
    override var rollbackName: String

    ): EventModel(status, rollbackName)