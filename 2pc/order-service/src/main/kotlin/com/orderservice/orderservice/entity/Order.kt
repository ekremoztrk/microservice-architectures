package com.orderservice.orderservice.entity

import java.math.BigDecimal
import javax.persistence.*


@Entity
@Table(name = "order_table")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val amount: Int,

    val productId: Long,

    val accountId: Long,

    val totalPrice: BigDecimal
)