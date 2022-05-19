package com.example.demo.app

import com.example.demo.model.Category
import com.example.demo.model.Product
import model.ProductPurchase
import java.time.LocalDate
import java.util.*


fun main() {
    println("SANDBOX")
    val p = Product(null,1234,"Name",Category.FOOD)
    p.purchaseHistory.add(ProductPurchase(null, LocalDate.of(2019, 12, 30),10,20.0))
    p.purchaseHistory.add(ProductPurchase(null, LocalDate.of(2018, 12, 30),10,15.0))
    println(p.getTotalPurchasePrice())
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2018, 12, 30),LocalDate.of(2018, 12, 30)))
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2019, 12, 30),LocalDate.of(2019, 12, 30)))
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2018, 12, 30),LocalDate.of(2019, 12, 30)))
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2017, 12, 30),LocalDate.of(2017, 12, 30)))
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2020, 12, 30),LocalDate.of(2020, 12, 30)))
    println(p.getTotalPurchasePriceBetweenDates(LocalDate.of(2018, 12, 30),LocalDate.now()))

}
