package com.orderservice.orderservice.repository

import com.orderservice.orderservice.entity.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: CrudRepository<Order, Long> {
}