package com.orchestrator.orchestrator.entity

import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "command_table")
data class Command(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val orderId: Long,

    var status: Status,

    val accountId: Long,

    val amountOfProduct: Int,

    var totalPrice: BigDecimal = BigDecimal.ZERO,

    val productId: Long,

    val createdDate: Instant? = Instant.now()
)
