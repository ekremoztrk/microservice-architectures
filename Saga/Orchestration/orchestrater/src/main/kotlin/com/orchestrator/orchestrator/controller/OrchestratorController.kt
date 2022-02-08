package com.orchestrator.orchestrator.controller

import com.orchestrator.orchestrator.model.OrderRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.orchestrator.orchestrator.service.Orchestrator
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("api/orchestrator")
class OrchestratorController(
    private val orchestrator: Orchestrator
) {

    @PostMapping("/order")
    fun startOrderTransactions(@RequestBody orderRequest: OrderRequest){
        orchestrator.startOrderCreation(orderRequest)
    }
}