package com.orderservice.orderservice.controller

import com.orderservice.orderservice.entity.Order
import com.orderservice.orderservice.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderService: OrderService
) {


    @PostMapping("/order")
    fun createOrder(@RequestParam("accountId") accountId: Long, @RequestParam("productId") productId: Long, @RequestParam("amount") amount: Int) : Order {
        return orderService.createOrder(accountId, productId, amount)
    }
}