package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import tornadofx.*

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class ProductStock(
    var amount: Int,
    var pricePerOne: Double,

    var date: LocalDateTime = LocalDateTime.now(),
) : JsonModel{

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