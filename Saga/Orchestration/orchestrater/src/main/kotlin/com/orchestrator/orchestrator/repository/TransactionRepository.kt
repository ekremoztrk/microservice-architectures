package com.orchestrator.orchestrator.repository

import com.orchestrator.orchestrator.entity.Status
import com.orchestrator.orchestrator.entity.Transaction
import com.orchestrator.orchestrator.entity.TransactionType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {

    fun findAllByCommandId(commandId: Long): List<Transaction>
    fun findAllByCommandIdAndStatusNotLike(commandId: Long, status: Status): List<Transaction>

    fun findByCommandIdAndTransactionType(commandId: Long, transactionType: TransactionType): Transaction?

}