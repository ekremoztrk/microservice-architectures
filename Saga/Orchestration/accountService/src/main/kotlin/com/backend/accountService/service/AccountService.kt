package com.backend.accountService.service

import com.backend.accountService.messageBus.Producer
import com.backend.accountService.model.PaymentEvent
import com.backend.accountService.model.Status
import com.backend.accountService.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val producer: Producer
) {

    fun processOrderPaymentRequest(paymentEvent: PaymentEvent) {
        if(paymentEvent.status == Status.ROLLBACK){
            var account = accountRepository.findById(paymentEvent.accountId).get()
            account.balance += paymentEvent.price
            accountRepository.save(account)
        } else {
            var account = accountRepository.findById(paymentEvent.accountId).get()
            if (account.balance < paymentEvent.price) {
                producer.sendExecutePayment(
                    PaymentEvent(
                        orderId = paymentEvent.orderId,
                        accountId = paymentEvent.accountId,
                        price = paymentEvent.price,
                        status = Status.ROLLBACK
                    )
                )
            }
            else {
                account.balance = account.balance - paymentEvent.price
                accountRepository.save(account)
                producer.sendExecutePayment(
                    PaymentEvent(orderId = paymentEvent.orderId,
                        accountId = paymentEvent.accountId,
                        price = paymentEvent.price,
                        status = Status.PROCESSED))
            }
        }
    }
}