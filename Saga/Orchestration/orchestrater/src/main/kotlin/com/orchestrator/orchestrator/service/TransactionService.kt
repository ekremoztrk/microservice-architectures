package com.orchestrator.orchestrator.service

import com.orchestrator.orchestrator.entity.Status
import com.orchestrator.orchestrator.entity.TransactionType
import com.orchestrator.orchestrator.model.TransactionCompletionModel
import com.orchestrator.orchestrator.repository.CommandRepository
import com.orchestrator.orchestrator.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val commandRepository: CommandRepository
){

    fun checkTransactionsCompletion(orderId: Long): TransactionCompletionModel{
        val command = commandRepository.findByOrderId(orderId)!!
        val transactions = transactionRepository.findAllByCommandIdAndStatusNotLike(command.id!!, Status.CREATED)

        if (transactions.size != 3){
            return TransactionCompletionModel(false)
        } else {
            for (tr in transactions){
                if (tr.status == Status.ROLLBACK)
                    return TransactionCompletionModel(isTransactionsCompleted = true, isTransactionsCompletedSuccessfully = false)
            }
            return TransactionCompletionModel(isTransactionsCompleted = true, isTransactionsCompletedSuccessfully = false)
        }

    }

    fun updateTransactionStatusByOrderIdAndType(orderId: Long, transactionType: TransactionType, status: Status){
        val command = commandRepository.findByOrderId(orderId)
        val transaction = transactionRepository.findByCommandIdAndTransactionType(command!!.id!!, transactionType)
        transaction!!.status = status
        transactionRepository.save(transaction)


    }
}