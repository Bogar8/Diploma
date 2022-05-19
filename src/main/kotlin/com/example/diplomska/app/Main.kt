package com.example.diplomska.app

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.util.DatabaseUtil
import com.mongodb.client.ClientSession
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.ProductStock
import org.bson.Document
import org.litote.kmongo.eq
import org.litote.kmongo.findOne


import org.litote.kmongo.findOneById
import org.litote.kmongo.json
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList



fun main() {
    val database = DatabaseUtil.getConnection()
    println("SANDBOX")
    val p = ProductStock(null, 10, 20.2)
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
        Product(UUID.randomUUID().toString(),12345, "name", Category.FOOD, 0, null, true, sellingHistory, purchaseHistory)


    val collection = database.getCollection(Product.DATABASE_NAME)
    collection.insertOne(Document.parse(Json.encodeToString(testSample)))
    val result = collection.findOneById(testSample._id)?.toJson()
    var list = ArrayList<Product>()
    println(collection.find().forEach{
        list.add(Json.decodeFromString(it.json))
    })
    println(list)
    println(list.size)

    if (result != null) {
        println(Json.decodeFromString<Product>(result).toString())
    }


}
