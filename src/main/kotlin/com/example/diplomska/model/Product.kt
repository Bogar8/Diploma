package com.example.diplomska.model

import com.example.diplomska.extensions.*
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList

import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class Product(
    _id: String = "",
    barcode: String = "",
    name: String = "",
    category: Category = Category.NONE,
    stock: Int = 0,
    sellingPrice: Double = 0.0,
    lastPurchasedPrice: Double = 0.0,
    isActive: Boolean = true,
    sellingHistory: ArrayList<ProductStock> = ArrayList(),
    purchaseHistory: ArrayList<ProductStock> = ArrayList(),
    lastChanged: LocalDateTime = LocalDateTime.now()
) : JsonModel {

    val _idProperty = SimpleStringProperty(_id)
    var _id: String by _idProperty

    val barcodeProperty = SimpleStringProperty(barcode)
    var barcode by barcodeProperty

    val nameProperty = SimpleStringProperty(name)
    var name: String by nameProperty

    val categoryProperty = SimpleObjectProperty(category)
    var category: Category by categoryProperty

    val stockProperty = SimpleIntegerProperty(stock)
    var stock by stockProperty

    val sellingPriceProperty = SimpleDoubleProperty(sellingPrice)
    var sellingPrice by sellingPriceProperty

    val lastPurchasedPriceProperty = SimpleDoubleProperty(lastPurchasedPrice)
    var lastPurchasedPrice by lastPurchasedPriceProperty

    val isActiveProperty = SimpleBooleanProperty(isActive)
    var isActive by isActiveProperty

    val sellingHistory: ObservableList<ProductStock> = FXCollections.observableArrayList<ProductStock>(sellingHistory)
    val purchaseHistory: ObservableList<ProductStock> = FXCollections.observableArrayList<ProductStock>(purchaseHistory)


    val lastChangedProperty = SimpleObjectProperty(lastChanged)
    var lastChanged: LocalDateTime by lastChangedProperty


    override fun updateModel(json: JsonObject) {
        with(json) {
            _id = string("_id")!!
            barcode = string("barcode")!!
            name = string("name")!!
            category = Category.valueOf(string("category")!!)
            stock = int("stock")!!
            sellingPrice = double("sellingPrice")!!
            lastPurchasedPrice = double("lastPurchasedPrice")!!
            isActive = boolean("isActive")!!
            if (getJsonArray("sellingHistory") != null)
                sellingHistory.setAll(getJsonArray("sellingHistory").toModel())
            if (getJsonArray("purchaseHistory") != null)
                purchaseHistory.setAll(getJsonArray("purchaseHistory").toModel())
            lastChanged = LocalDateTime.parse(string("lastChanged"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("_id", _id)
            add("barcode", barcode)
            add("name", name)
            add("category", category.name)
            add("stock", stock)
            add("sellingPrice", sellingPrice)
            add("lastPurchasedPrice", lastPurchasedPrice)
            add("isActive", isActive)
            add("sellingHistory", sellingHistory.toJSON())
            add("purchaseHistory", purchaseHistory.toJSON())
            add("lastChanged", lastChanged.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        }
    }

    companion object {
        val DATABASE_NAME = "Products"
    }

    override fun toString(): String {
        return "id:$_id barcode:$barcode name:$name category:${category.categoryName} stock:$stock last changed: ${lastChanged.toNiceString()} is active:$isActive\n" +
                "selling history:$sellingHistory\npurchase history:$purchaseHistory"
    }

    fun getCurrentSellingPrice(): Double {
        return sellingPrice
    }

    fun getCurrentPurchasedPrice(): Double {
        return lastPurchasedPrice
    }

    fun getProfit(): Double {
        return this.getTotalSoldPrice() - this.getTotalPurchasedPrice()
    }

    fun getProfitBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return this.getTotalSoldPriceBetweenDates(dateFrom, dateTo) -
                this.getTotalPurchasedPriceBetweenDates(dateFrom, dateTo)
    }

    fun getTotalPurchasedPrice(): Double {
        return purchaseHistory.getTotalPrice()
    }

    fun getTotalPurchasedPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return purchaseHistory.getTotalPriceBetweenDates(dateFrom, dateTo)
    }

    fun getTotalPurchasedAmount(): Int {
        return purchaseHistory.getTotalAmount()
    }

    fun getTotalPurchasedAmountBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Int {
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
