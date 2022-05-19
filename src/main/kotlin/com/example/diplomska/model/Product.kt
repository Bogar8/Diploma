package com.example.diplomska.model

import com.example.diplomska.extensions.getTotalAmount
import com.example.diplomska.extensions.getTotalAmountBetweenDates
import com.example.diplomska.extensions.getTotalPrice
import com.example.diplomska.extensions.getTotalPriceBetweenDates
import model.ProductStock
import java.awt.Image
import java.time.LocalDateTime

class Product(
    var _id: String?,
    var barcode: Int,
    var name: String,
    var category: Category,
    var sellingHistory: ArrayList<ProductStock> = ArrayList(),
    var purchaseHistory: ArrayList<ProductStock> = ArrayList(),
    var stock: Int = 0,
    var image: Image? = null,
    var isActive: Boolean = true
) {
    fun getTotalPurchasePrice(): Double {
        return purchaseHistory.getTotalPrice()
    }

    fun getTotalPurchasePriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return purchaseHistory.getTotalPriceBetweenDates(dateFrom, dateTo)
    }

    fun getTotalPurchaseAmount(): Int {
        return purchaseHistory.getTotalAmount()
    }

    fun getTotalPurchaseAmountBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Int {
        return purchaseHistory.getTotalAmountBetweenDates(dateFrom, dateTo)
    }

    fun getTotalSoldPrice(): Double {
        return sellingHistory.getTotalPrice()
    }

    fun getTotalSoldPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return sellingHistory.getTotalPriceBetweenDates(dateFrom, dateTo)
    }

    fun getTotalSoldAmount(): Int {
        return sellingHistory.getTotalAmount()
    }

    fun getTotalSoldAmountBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Int {
        return sellingHistory.getTotalAmountBetweenDates(dateFrom, dateTo)
    }
}
