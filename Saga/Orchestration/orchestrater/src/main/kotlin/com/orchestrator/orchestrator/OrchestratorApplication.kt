package com.orchestrator.orchestrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class OrchestratorApplication

fun main(args: Array<String>) {
	runApplication<OrchestratorApplication>(*args)
}
