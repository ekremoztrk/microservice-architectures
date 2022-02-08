package com.orderservice.orderservice.cllient

import com.orderservice.orderservice.model.UpdateBalanceModel
import com.orderservice.orderservice.model.UpdateProductModel
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder

@Component
class ProductWebService (
    private val restTemplate: RestTemplate
){
    fun isProductPrepareForOrder(productId: Long, amount: Int): UpdateProductModel {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8082/product/")
                .path("$productId/amount/$amount")
                .build()
                .toUri(),
            null,
        )
    }

    fun commit(): String {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8082/product/")
                .path("commit")
                .build()
                .toUri(),
            null,
        )
    }

    fun backToSave(savePoint: String): String {

        return restTemplate.postForObject(
            UriComponentsBuilder
                .fromHttpUrl("http://localhost:8082/product/")
                .path("$savePoint")
                .build()
                .toUri(),
            null,
        )
    }
}