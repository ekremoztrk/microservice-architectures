package com.orchestrator.orchestrator.service

import com.orchestrator.orchestrator.entity.*
import com.orchestrator.orchestrator.messageBus.Producer
import com.orchestrator.orchestrator.model.*
import com.orchestrator.orchestrator.repository.CommandRepository
import com.orchestrator.orchestrator.repository.OrderRepository
import com.orchestrator.orchestrator.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class Orchestrator(
    private val commandRepository: CommandRepository,
    private val orderRepository: OrderRepository,
    private val transactionRepository: TransactionRepository,
    private val transactionService: TransactionService,
    private val producer: Producer
) {

    fun startOrderCreation(orderRequest: OrderRequest){
        val order = orderRepository.save(OrderTable())
        orderRequest.orderId = order.id
        val command: Command = commandRepository.save(Command(orderId = orderRequest.orderId!!,
            status = Status.CREATED,
            accountId = orderRequest.accountId,
            amountOfProduct = orderRequest.amountOfProduct,
            totalPrice = orderRequest.productUnitPrice!!.times(BigDecimal(orderRequest.amountOfProduct)),
            productId = orderRequest.productId))

        val transaction: Transaction = transactionRepository.save(Transaction(commandId = command.id!!, status = Status.CREATED, transactionType = TransactionType.PRODUCT_TRANSACTION))
        val transaction2: Transaction = transactionRepository.save(Transaction(commandId = command.id!!, status = Status.CREATED, transactionType = TransactionType.PAYMENT_TRANSACTION))
        val transaction3: Transaction = transactionRepository.save(Transaction(commandId = command.id!!, status = Status.CREATED, transactionType = TransactionType.DELIVERY_TRANSACTION))

        producer.sendExecuteStock(StockEvent(orderId = orderRequest.orderId!!,
                                            productId = orderRequest.productId,
                                            amount = orderRequest.amountOfProduct,
                                            status = Status.CREATED))

        producer.sendExecutePayment(PaymentEvent(orderId = command.orderId,
                                                accountId = command.accountId,
                                                price = orderRequest.productUnitPrice.times(BigDecimal(orderRequest.amountOfProduct)),
                                                status = Status.CREATED))

        producer.sendExecuteDelivery(DeliveryEvent(orderId = command.orderId,
                                                status = Status.CREATED))
    }

    fun eventReply(orderSagaReplyModel: OrderSagaReplyModel){
        transactionService.updateTransactionStatusByOrderIdAndType(orderSagaReplyModel.orderId, orderSagaReplyModel.transactionType, orderSagaReplyModel.status)
        val transactionCompletion = transactionService.checkTransactionsCompletion(orderSagaReplyModel.orderId)

        if (transactionCompletion.isTransactionsCompleted && transactionCompletion.isTransactionsCompletedSuccessfully!!)
            finishOrder(orderSagaReplyModel.orderId)
        else if (transactionCompletion.isTransactionsCompleted && !transactionCompletion.isTransactionsCompletedSuccessfully!!)
            rollBackOrder(orderSagaReplyModel.orderId)
    }

    private fun rollBackOrder(orderId: Long){

        val command = commandRepository.findByOrderId(orderId)!!
        val transactions = transactionRepository.findAllByCommandId(command.id!!)

        for (transaction in transactions){
            if (transaction.transactionType == TransactionType.PRODUCT_TRANSACTION && transaction.status != Status.ROLLBACK) {
                transactionService.updateTransactionStatusByOrderIdAndType(orderId, TransactionType.PRODUCT_TRANSACTION, Status.ROLLBACK)
                producer.sendExecuteStock(StockEvent(orderId = orderId, status = Status.ROLLBACK, amount = command.amountOfProduct, productId = command.productId))
            } else if(transaction.transactionType == TransactionType.PAYMENT_TRANSACTION && transaction.status != Status.ROLLBACK) {
                transactionService.updateTransactionStatusByOrderIdAndType(orderId, TransactionType.PAYMENT_TRANSACTION, Status.ROLLBACK)
                producer.sendExecutePayment(PaymentEvent(accountId = command.accountId,orderId = orderId, status = Status.ROLLBACK, price = command.totalPrice))
            } else if(transaction.transactionType == TransactionType.DELIVERY_TRANSACTION && transaction.status != Status.ROLLBACK) {
                transactionService.updateTransactionStatusByOrderIdAndType(orderId, TransactionType.DELIVERY_TRANSACTION, Status.ROLLBACK)
                producer.sendExecuteDelivery(DeliveryEvent(orderId = orderId, status = Status.ROLLBACK))
            }
        }
    }

    fun finishOrder(orderId: Long){
        val command: Command = commandRepository.findByOrderId(orderId)!!
        val transactions = transactionRepository.findAllByCommandId(command.id!!)

        command.status = Status.COMPLETED
        for (transaction in transactions) {
            transaction.status = Status.COMPLETED
        }
        commandRepository.save(command)
        transactionRepository.saveAll(transactions)
    }

    /*
    fun startOrderCreation(orderRequest: OrderRequest){
        val command: Command = commandRepository.save(Command(orderId = orderRequest.orderId,
                                                            status = Status.CREATED,
                                                            accountId = orderRequest.accountId,
                                                            amountOfProduct = orderRequest.amountOfProduct))

        val transaction: Transaction = transactionRepository.save(Transaction(commandId = command.id!!,
                                                                            status = Status.CREATED,
                                                                            transactionType = TransactionType.PRODUCT_TRANSACTION))

        producer.sendExecuteStock(StockEvent(orderId = orderRequest.orderId,
                                            productId = orderRequest.productId,
                                            amount = orderRequest.amountOfProduct,
                                            status = Status.CREATED))
    }

    fun stockExecuted(stockEvent: StockEvent){
        if(stockEvent.status == Status.ERROR){
            rollBackFirstStep(stockEvent.orderId, TransactionType.DELIVERY_TRANSACTION)
        } else {
            val command: Command = commandRepository.findByOrderId(stockEvent.orderId)!!
            command.totalPrice = stockEvent.price!!.times(BigDecimal(stockEvent.amount))
            commandRepository.save(command)

            val transaction: Transaction = transactionRepository.save(Transaction(commandId = command.id!!,
                                                                                status = Status.CREATED,
                                                                                transactionType = TransactionType.PAYMENT_TRANSACTION))

            producer.sendExecutePayment(PaymentEvent(orderId = command.orderId,
                                                    accountId = command.accountId,
                                                    price = stockEvent.price!!,
                                                    status = Status.CREATED))

        }
    }

    fun paymentExecuted(paymentEvent: PaymentEvent) {
        if(paymentEvent.status == Status.ERROR){
            rollBackFirstStep(paymentEvent.orderId, TransactionType.DELIVERY_TRANSACTION)
        } else {
            val command: Command = commandRepository.findByOrderId(paymentEvent.orderId)!!

            val transaction: Transaction = transactionRepository.save(Transaction(commandId = command.id!!,
                                                                                status = Status.CREATED,
                                                                                transactionType = TransactionType.DELIVERY_TRANSACTION))

            producer.sendExecuteDelivery(DeliveryEvent(orderId = command.orderId,
                                                    status = Status.CREATED))

        }
    }

    fun deliveryExecuted(deliveryEvent: DeliveryEvent) {
        if(deliveryEvent.status == Status.ERROR){
            rollBackFirstStep(deliveryEvent.orderId, TransactionType.DELIVERY_TRANSACTION)
        } else {
            val command: Command = commandRepository.findByOrderId(deliveryEvent.orderId)!!

            val transactions = transactionRepository.findAllByOrderId(command.orderId)

            for (transaction in transactions) {
                transaction.status = Status.COMPLETED
            }
            transactionRepository.saveAll(transactions)
        }
    }

    private fun rollBackFirstStep(orderId: Long, transactionType: TransactionType){
        updateTransactionStatusByOrderIdAndType(orderId, transactionType, Status.ROLLBACK)
        rollBackOrder(orderId)
    }

    private fun rollBackOrder(orderId: Long){

        val transactions = transactionRepository.findAllByOrderId(orderId)
        val command = commandRepository.findByOrderId(orderId)!!

        for (transaction in transactions){
            if (transaction.transactionType == TransactionType.PRODUCT_TRANSACTION && transaction.status != Status.ROLLBACK) {
                updateTransactionStatusByOrderIdAndType(orderId, TransactionType.PRODUCT_TRANSACTION, Status.ROLLBACK)
                producer.sendExecuteStock(StockEvent(orderId = orderId, status = Status.ROLLBACK, amount = command.amountOfProduct ))
            } else if(transaction.transactionType == TransactionType.PAYMENT_TRANSACTION && transaction.status != Status.ROLLBACK) {
                updateTransactionStatusByOrderIdAndType(orderId, TransactionType.PAYMENT_TRANSACTION, Status.ROLLBACK)
                producer.sendExecutePayment(PaymentEvent(orderId = orderId, status = Status.ROLLBACK, price = command.totalPrice))
            } else if(transaction.transactionType == TransactionType.DELIVERY_TRANSACTION && transaction.status != Status.ROLLBACK) {
                updateTransactionStatusByOrderIdAndType(orderId, TransactionType.DELIVERY_TRANSACTION, Status.ROLLBACK)
                producer.sendExecuteDelivery(DeliveryEvent(orderId = orderId, status = Status.ROLLBACK))
            }
        }

    }

    private fun updateTransactionStatusByOrderIdAndType(orderId: Long, transactionType: TransactionType, status: Status){
        val transaction = transactionRepository.findByOrderIdAndTransactionType(orderId, transactionType)
        transaction.status = status
        transactionRepository.save(transaction)
    }

     */
}