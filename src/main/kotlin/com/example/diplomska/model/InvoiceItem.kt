package com.example.diplomska.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject


class InvoiceItem(
    productName: String = "",
    amount: Int = 0,
    totalPrice: Double = 0.0,
    pricePerOne: Double = 0.0
) : JsonModel {

    var productNameProperty = SimpleStringProperty(productName)
    var productName: String by productNameProperty

    var amountProperty = SimpleIntegerProperty(amount)
    var amount by amountProperty

    var totalPriceProperty = SimpleDoubleProperty(totalPrice)
    var totalPrice by totalPriceProperty

    var pricePerOneProperty = SimpleDoubleProperty(pricePerOne)
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