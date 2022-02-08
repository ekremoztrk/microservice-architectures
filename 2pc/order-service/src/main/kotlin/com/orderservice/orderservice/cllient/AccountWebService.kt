package com.orderservice.orderservice.cllient

import com.orderservice.orderservice.model.UpdateBalanceModel
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal

@Component
class AccountWebService(
    private val restTemplate: RestTemplate
) {
    fun isAccountPrepareForOrder(accountId: Long, neededBalance: BigDecimal): UpdateBalanceModel {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8081/account/")
                .path("$accountId/amount/$neededBalance")
                .build()
                .toUri(),
            null,
        )
    }

    fun commit(): String {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8081/account/")
                .path("commit")
                .build()
                .toUri(),
            null,
        )
    }
    fun backToSave(savePoint: String): String {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8081/account/")
                .path("$savePoint")
                .build()
                .toUri(),
            null,
        )
    }
}