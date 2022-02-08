package com.productservice.productservice.service

import com.productservice.productservice.model.Status
import com.productservice.productservice.model.UpdateProductModel
import com.productservice.productservice.repository.ProductRepository
import net.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.*

@Service
class ProductService(
    val productRepository: ProductRepository
) {
    var conn: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productDb", "root", "1234")
    var savePointList = mutableListOf<Savepoint>()

    fun prepareProduct(productId:Long, amount: Int): UpdateProductModel{
        conn.autoCommit = false
        val spt: Savepoint = conn.setSavepoint(RandomString.make())
        savePointList.add(spt)

        return try {

            val product = productRepository.findById(productId).orElseThrow()
            val preparedS: PreparedStatement = conn.prepareStatement("update Product set amount=amount-$amount where id = $productId")
            preparedS.execute()
            lockTable("Product")
            UpdateProductModel(productId,amount, product.price, Status.SUCCESS, spt.savepointName)
        } catch (throwable: Exception) {
            throwable.printStackTrace()
            UpdateProductModel(productId,amount, BigDecimal.ZERO, Status.FAILED, spt.savepointName)
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
    }
}