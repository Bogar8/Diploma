package com.example.diplomska.app

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

}
