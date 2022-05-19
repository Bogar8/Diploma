package com.example.diplomska.app

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.ProductStock
import java.time.LocalDateTime


fun main() {
    println("SANDBOX")
    val p = ProductStock(null,10,20.2)
    val format = Json { prettyPrint = true }
    var json = format.encodeToString(p)
    println(json)
    val json2 = format.decodeFromString<ProductStock>(json)
    println(json2.toString())

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
     val testSample: Product =
        Product(null, 12345, "name", Category.FOOD, purchaseHistory = purchaseHistory, sellingHistory = sellingHistory)

    println(testSample.toString())
}
