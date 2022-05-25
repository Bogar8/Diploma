package com.example.diplomska.app

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.example.diplomska.model.ProductStock
import com.example.diplomska.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.json


import java.time.LocalDateTime
import java.util.*
import javax.print.Doc
import kotlin.collections.ArrayList


fun main() {
    println("SANDBOX")

//
//    val purchaseHistory: ArrayList<ProductStock> = arrayListOf(
//        ProductStock(10, 20.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
//        ProductStock(10, 15.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
//        ProductStock(50, 18.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
//        ProductStock(5, 17.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
//        ProductStock(1, 99.99, LocalDateTime.of(2020, 1, 2, 12, 0))
//    )
//    val sellingHistory: ArrayList<ProductStock> = arrayListOf(
//        ProductStock(10, 25.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
//        ProductStock(10, 20.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
//        ProductStock(50, 19.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
//        ProductStock(5, 18.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
//        ProductStock(1, 101.99, LocalDateTime.of(2020, 1, 2, 12, 0))
//    )
//    val testSample =
//        Product(
//            UUID.randomUUID().toString(),
//            12345,
//            "name",
//            Category.FOOD,
//            0,
//            null,
//            true,
//            sellingHistory,
//            purchaseHistory
//        )
//
//
//    val collection = DatabaseUtil.getDatabaseConnection().getCollection(Product.DATABASE_NAME)
//    collection.insertOne(DocumentUtil.encode(testSample))
//
//    val answer = collection.findOne(Product::_id eq testSample._id)
//
//    if (answer != null) {
//        println(DocumentUtil.decode<Product>(answer).toString())
//    }

    val user = User(UUID.randomUUID().toString(), "name3", "surname3", "username3", "password3", 2)
     println(UserDatabase.insert(user))
    println(UserDatabase.getAll())
    println(UserDatabase.getByLevel(1))
    println(UserDatabase.getByUsername("username3"))
    println(UserDatabase.getLastLoggedInAfterDate(LocalDateTime.now()))
    var users = UserDatabase.getAll()
    users[0].password="newpassword"
    UserDatabase.update(users[0])
    UserDatabase.delete(users[1])

}