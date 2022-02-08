package com.orchestrator.orchestrator.service

import com.orchestrator.orchestrator.entity.Status
import com.orchestrator.orchestrator.repository.CommandRepository
import org.springframework.stereotype.Service

@Service
class CommandService(
    private val commandRepository: CommandRepository,
    private val transactionService: TransactionService
) {


    fun changeCommandStatusWithOrderId(orderId: Long, status: Status){
        val command = commandRepository.findByOrderId(orderId)!!
        command.status = status
        commandRepository.save(command)
    }


}