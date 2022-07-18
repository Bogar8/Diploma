package com.example.diplomska.model

import com.example.diplomska.extensions.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class Product(
    _id: String,
    barcode: Int,
    name: String,
    category: Category,
    stock: Int,
    imagePath: String?,
    isActive: Boolean,
    sellingHistory: ArrayList<ProductStock> = ArrayList(),
    purchaseHistory: ArrayList<ProductStock> = ArrayList(),
    lastChanged: LocalDateTime = LocalDateTime.now()
) : JsonModel {

    var _idProperty = SimpleStringProperty(_id)
    var _id by _idProperty


    var barcodeProperty = SimpleIntegerProperty(barcode)
    var barcode by barcodeProperty


    var nameProperty = SimpleStringProperty(name)
    var name by nameProperty


    var categoryProperty = SimpleObjectProperty(category)
    var category by categoryProperty

    var stockProperty = SimpleIntegerProperty(stock)
    var stock by stockProperty


    var imagePathProperty = SimpleStringProperty(imagePath)
    var imagePath by imagePathProperty

    var isActiveProperty = SimpleBooleanProperty(isActive)
    var isActive by isActiveProperty

    var sellingHistory = FXCollections.observableArrayList<ProductStock>(sellingHistory)
    var purchaseHistory = FXCollections.observableArrayList<ProductStock>(purchaseHistory)

    var lastChangedProperty = SimpleObjectProperty(lastChanged)
    var lastChanged by lastChangedProperty


    override fun updateModel(json: JsonObject) {
        with(json) {
            _id = string("_id")!!
            barcode = int("barcode")!!
            name = string("name")!!
            category = Category.valueOf(string("category")!!)
            stock = int("stock")!!
            imagePath = string("imagePath")!!
            isActive = boolean("isActive")!!
            sellingHistory.setAll(getJsonArray("sellingHistory").toModel())
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
            add("imagePath", imagePath)
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
        return sellingHistory.last().pricePerOne
    }

    fun getCurrentPurchasePrice(): Double {
        return purchaseHistory.last().pricePerOne
    }

    fun getProfit(): Double {
        return this.getTotalSoldPrice() - this.getTotalPurchasePrice()
    }

    fun getProfitBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
        return this.getTotalSoldPriceBetweenDates(dateFrom, dateTo) -
                this.getTotalPurchasePriceBetweenDates(dateFrom, dateTo)
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
