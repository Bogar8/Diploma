package com.example.diplomska.model

import com.example.diplomska.extensions.getTotalAmount
import com.example.diplomska.extensions.getTotalAmountBetweenDates
import com.example.diplomska.extensions.getTotalPrice
import com.example.diplomska.extensions.getTotalPriceBetweenDates
import model.ProductStock
import java.awt.Image
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
class Product(
    var _id: String?,
    var barcode: Int,
    var name: String,
    var category: Category,
    var stock: Int = 0,
    var imagePath: String? = null,
    var isActive: Boolean = true,
    private var sellingHistory: ArrayList<ProductStock> = ArrayList(),
    private var purchaseHistory: ArrayList<ProductStock> = ArrayList()
) {

    override fun toString(): String {
        return "id:$_id barcode:$barcode name:$name category:$category stock:$stock is active:$isActive\n" +
                "selling history:${sellingHistory.toString()}\npurchase history:${purchaseHistory.toString()}"
    }

    fun getCurrentSellingPrice(): Double {
        return sellingHistory.last().pricePerOne
    }

    fun getCurrentPurchasePrice(): Double {
        return purchaseHistory.last().pricePerOne
    }

    fun getProfit(): Double {
        return this.getTotalSoldPrice() - this.getTotalPurchasePrice()
    }

    fun getProfitBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return this.getTotalSoldPriceBetweenDates(dateFrom, dateTo) - this.getTotalPurchasePriceBetweenDates(
            dateFrom,
            dateTo
        )
    }

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
