package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class ProductStock(
    amount: Int = 0,
    pricePerOne: Double = 0.0,
    date: LocalDateTime = LocalDateTime.now(),
) : JsonModel {

    val amountProperty = SimpleIntegerProperty(amount)
    var amount by amountProperty

    val pricePerOneProperty = SimpleDoubleProperty(pricePerOne)
    var pricePerOne by pricePerOneProperty

    val dateProperty = SimpleObjectProperty(date)
    var date: LocalDateTime by dateProperty


    override fun updateModel(json: JsonObject) {
        with(json) {
            amount = int("amount")!!
            pricePerOne = double("pricePerOne")!!
            date = LocalDateTime.parse(string("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("amount", amount)
            add("pricePerOne", pricePerOne)
            add("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        }
    }

    override fun toString(): String {
        return "\ndate: ${date.toNiceString()} amount: $amount price per one: ${pricePerOne}€"
    }
}