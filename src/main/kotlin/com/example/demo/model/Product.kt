package com.example.demo.model

import model.ProductPurchase
import model.ProductSellingPrice
import java.awt.Image
import java.time.LocalDate

class Product(
    var _id: String?,
    var barcode: Int,
    var name: String,
    var category: Category,
    var sellingPriceHistory: ArrayList<ProductSellingPrice> = ArrayList(),
    var purchaseHistory: ArrayList<ProductPurchase> = ArrayList(),
    var numberOfSold: Int = 0,
    var stock: Int = 0,
    var image: Image? = null,
    var isActive: Boolean = true
) {
    fun getTotalPurchasePrice(): Double {
        var totalPrice: Double = 0.0
        purchaseHistory.forEach {
            totalPrice += it.purchasePrice * it.amount
        }
        return totalPrice
    }

    fun getTotalPurchasePriceBetweenDates(dateFrom: LocalDate, dateTo: LocalDate) : Double {
        var totalPrice: Double = 0.0
        purchaseHistory.forEach {
            if (it.date in dateFrom..dateTo) {
                totalPrice += it.purchasePrice * it.amount
            }
        }
        return totalPrice
    }
}
