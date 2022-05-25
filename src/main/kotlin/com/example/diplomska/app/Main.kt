package com.example.diplomska.app

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.example.diplomska.model.ProductStock
import org.litote.kmongo.eq
import org.litote.kmongo.findOne


import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


fun main() {
    val database = DatabaseUtil.getConnection()
    println("SANDBOX")


    val purchaseHistory: ArrayList<ProductStock> = arrayListOf(
        ProductStock(null, 10, 20.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
        ProductStock(null, 10, 15.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
        ProductStock(null, 50, 18.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
        ProductStock(null, 5, 17.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
        ProductStock(null, 1, 99.99, LocalDateTime.of(2020, 1, 2, 12, 0))
    )
    val sellingHistory: ArrayList<ProductStock> = arrayListOf(
        ProductStock(null, 10, 25.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
        ProductStock(null, 10, 20.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
        ProductStock(null, 50, 19.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
        ProductStock(null, 5, 18.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
        ProductStock(null, 1, 101.99, LocalDateTime.of(2020, 1, 2, 12, 0))
    )
    val testSample =
        Product(
            UUID.randomUUID().toString(),
            12345,
            "name",
            Category.FOOD,
            0,
            null,
            true,
            sellingHistory,
            purchaseHistory
        )


    val collection = database.getCollection(Product.DATABASE_NAME)
    collection.insertOne(DocumentUtil.encode(testSample))

    val answer = collection.findOne(Product::_id eq testSample._id)


    if (answer != null) {
        println(DocumentUtil.decode<Product>(answer).toString())
    }
}