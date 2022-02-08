package com.accountservice.accountservice

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class AccountServiceApplication

fun main(args: Array<String>) {
	runApplication<AccountServiceApplication>(*args)
}
