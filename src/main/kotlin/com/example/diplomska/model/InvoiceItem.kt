package com.example.diplomska.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject
import kotlin.math.roundToInt


class InvoiceItem(
    productName: String = "",
    amount: Int = 0,
    pricePerOne: Double = 0.0,
    totalPrice: Double = ((amount * pricePerOne) * 100).roundToInt() / 100.0
) : JsonModel {

    val productNameProperty = SimpleStringProperty(productName)
    var productName: String by productNameProperty

    val amountProperty = SimpleIntegerProperty(amount)
    var amount by amountProperty

    val totalPriceProperty = SimpleDoubleProperty(totalPrice)
    var totalPrice by totalPriceProperty

    val pricePerOneProperty = SimpleDoubleProperty(pricePerOne)
    var pricePerOne by pricePerOneProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            productName = string("productName")!!
            amount = int("amount")!!
            totalPrice = double("totalPrice")!!
            pricePerOne = double("pricePerOne")!!
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("productName", productName)
            add("amount", amount)
            add("totalPrice", totalPrice)
            add("pricePerOne", pricePerOne)
        }
    }

    override fun toString(): String {
        return "product name:$productName amount:$amount price per one:$pricePerOne total price:$totalPrice"
    }
}