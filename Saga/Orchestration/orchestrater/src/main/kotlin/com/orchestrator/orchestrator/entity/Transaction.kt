package com.orchestrator.orchestrator.entity

import java.time.Instant
import javax.persistence.*

@Entity
data class Transaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val commandId: Long,

    var status: Status,

    val transactionType: TransactionType,

    val createdDate: Instant? = Instant.now()

)
