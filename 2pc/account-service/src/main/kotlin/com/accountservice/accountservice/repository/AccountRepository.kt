package com.accountservice.accountservice.repository

import com.accountservice.accountservice.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: CrudRepository<Account, Long> {

}