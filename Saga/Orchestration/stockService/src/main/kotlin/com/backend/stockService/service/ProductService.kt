package com.backend.stockService.service

import com.backend.stockService.entity.Product
import com.backend.stockService.messageBus.Producer
import com.backend.stockService.model.Status
import com.backend.stockService.model.StockEvent
import com.backend.stockService.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val producer: Producer
) {

    fun processOrderProductRequest(stockEvent: StockEvent) {
        if(stockEvent.status == Status.ROLLBACK) {
            val product: Product = productRepository.findById(stockEvent.productId).get()
            product.amount += stockEvent.amount
            productRepository.save(product)
        } else {
            val product: Product = productRepository.findById(stockEvent.productId).get()

            if(product.amount < stockEvent.amount)
                producer.sendExecuteStock(StockEvent(orderId = stockEvent.orderId,
                    productId = product.id,
                    amount = stockEvent.amount,
                    status = Status.ROLLBACK))
            else {
                product.amount = product.amount - stockEvent.amount
                productRepository.save(product)
                producer.sendExecuteStock(StockEvent(orderId = stockEvent.orderId,
                    productId = product.id,
                    amount = stockEvent.amount,
                    status = Status.PROCESSED))
            }
        }
    }
}