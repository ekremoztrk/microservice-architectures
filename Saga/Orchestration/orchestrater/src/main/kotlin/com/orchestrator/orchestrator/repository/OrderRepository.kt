package com.orchestrator.orchestrator.repository

import com.orchestrator.orchestrator.entity.OrderTable
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<OrderTable, Long> {
}