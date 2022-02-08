package com.productservice.productservice.controller

import com.productservice.productservice.model.UpdateProductModel
import com.productservice.productservice.service.ProductService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ProductController(
    val productService: ProductService
) {
    @PostMapping("/product/{product-id}/amount/{amount}")
    fun updateBalance(@PathVariable("product-id") productId: Long, @PathVariable("amount") amount: Int): UpdateProductModel {
        return  productService.prepareProduct(productId, amount)
    }

    @PostMapping("/product/{save-point}")
    fun backToSave(@PathVariable("save-point") savePoint: String): String{
        productService.backToSave(savePoint)
        return "x"
    }

    @PostMapping("/product/commit")
    fun commit(): String{
        productService.commitConnection()
        return "x"
    }
}