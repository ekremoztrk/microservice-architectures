package com.accountservice.accountservice.service

import com.accountservice.accountservice.component.EventComponent
import com.accountservice.accountservice.model.Status
import com.accountservice.accountservice.model.UpdateBalanceModel
import com.accountservice.accountservice.repository.AccountRepository
import net.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.*
import java.util.concurrent.TimeUnit


@Service
class AccountService(
    val accountRepository: AccountRepository,
    val messageProducer: EventComponent
) {
    var conn: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accountDb", "root", "1234")
    var savePointList = mutableListOf<Savepoint>()

    fun prepareBalance(accountId: Long, amount: BigDecimal): UpdateBalanceModel {
        conn.autoCommit = false
        val spt: Savepoint = conn.setSavepoint(RandomString.make())
        savePointList.add(spt)

        return try {

            val account = accountRepository.findById(accountId).orElseThrow()
            val preparedS: PreparedStatement = conn.prepareStatement("update Account set balance=balance-$amount where id = $accountId")
            preparedS.execute()
            lockTable("Account")
            UpdateBalanceModel(accountId,amount,Status.SUCCESS, spt.savepointName)
        } catch (throwable: Exception) {
            throwable.printStackTrace()
            UpdateBalanceModel(accountId,amount,Status.FAILED, spt.savepointName)
        }
    }

    fun backToSave(svpName: String){
        var savePoint: Savepoint? = null
        savePointList.forEach{
            s->if (s.savepointName.equals(svpName)){
                    savePoint = s
                }
        }
        if(savePoint!=null){
            unlockTables()
            conn.rollback()
            conn.commit()
        }
    }

    fun commitConnection(){
        unlockTables()
        conn.commit()
    }

    fun closeConnection(){
        conn.close()
    }

    private fun lockTable(tableName: String){
        val statement: Statement = conn.createStatement()
        statement.execute("LOCK TABLE $tableName WRITE;")

    }
    private fun unlockTables(){
        val statement: Statement = conn.createStatement()
        statement.execute("UNLOCK TABLES")
        statement.close()
    }
}