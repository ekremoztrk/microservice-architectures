package com.accountservice.accountservice.controller

import com.accountservice.accountservice.model.UpdateBalanceModel
import com.accountservice.accountservice.service.AccountService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal


@RestController
class AccountController(val accountService: AccountService) {


    @PostMapping("/account/{account-id}/amount/{amount}")
    fun updateBalance(@PathVariable("account-id") accountId: Long, @PathVariable("amount") amount: BigDecimal): UpdateBalanceModel {
       return  accountService.prepareBalance(accountId, amount)
    }

    @PostMapping("/account/{save-point}")
    fun backToSave(@PathVariable("save-point") savePoint: String): String{
        accountService.backToSave(savePoint)
        return "x"
    }

    @PostMapping("/account/commit")
    fun commit(): String{
        accountService.commitConnection()
        return "x"
    }
}