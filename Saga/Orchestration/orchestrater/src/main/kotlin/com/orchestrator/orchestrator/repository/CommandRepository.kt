package com.orchestrator.orchestrator.repository

import com.orchestrator.orchestrator.entity.Command
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommandRepository: CrudRepository<Command, Long> {

    fun findByOrderId(orderId: Long): Command?
}