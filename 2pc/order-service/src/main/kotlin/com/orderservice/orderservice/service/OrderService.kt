package com.orderservice.orderservice.service

import com.orderservice.orderservice.cllient.AccountWebService
import com.orderservice.orderservice.cllient.ProductWebService
import com.orderservice.orderservice.component.EventComponent
import com.orderservice.orderservice.entity.Order
import com.orderservice.orderservice.exception.PrepareException
import com.orderservice.orderservice.model.EventModel
import com.orderservice.orderservice.model.Status
import com.orderservice.orderservice.model.UpdateBalanceModel
import com.orderservice.orderservice.model.UpdateProductModel
import com.orderservice.orderservice.repository.OrderRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.system.exitProcess

@Service
class OrderService(
    val orderRepository: OrderRepository,
    private val accountWebService: AccountWebService,
    private val productWebService: ProductWebService,
    private val eventComponent: EventComponent
) {

    fun createOrder(accountId: Long, productId: Long, amount: Int) :Order{

        try {
            val isPreparedProduct: UpdateProductModel = productWebService.isProductPrepareForOrder(productId, amount)
            if(isPreparedProduct.status == Status.FAILED){
                println("fail")
                throw PrepareException("error while preparing product",mutableListOf(isPreparedProduct.rollbackName))
            }

            val totalPrice: BigDecimal = isPreparedProduct.price.times(BigDecimal(amount))
            val isPreparedAccount: UpdateBalanceModel = accountWebService.isAccountPrepareForOrder(accountId, totalPrice)
            if(isPreparedAccount.status == Status.FAILED){
                println("fail")
                throw PrepareException("error while preparing account",mutableListOf(isPreparedAccount.rollbackName, isPreparedProduct.rollbackName))
            }
            val order: Order = Order(0, amount, productId, accountId, totalPrice)
            orderRepository.save(order)
            eventComponent.sendProcessFinished(EventModel(Status.SUCCESS,""))
            return order
        }catch (exception: PrepareException){
            exception.rollBackName().forEach{ r ->
                accountWebService.backToSave(r)
                productWebService.backToSave(r)
            }
            throw exception
        }
    }
}

