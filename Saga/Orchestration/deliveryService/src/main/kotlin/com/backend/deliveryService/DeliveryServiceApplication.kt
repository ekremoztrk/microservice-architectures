package com.backend.deliveryService

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class DeliveryServiceApplication

fun main(args: Array<String>) {
	runApplication<DeliveryServiceApplication>(*args)
}
