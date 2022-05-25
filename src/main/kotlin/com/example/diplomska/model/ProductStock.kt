package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.util.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
class ProductStock(
    var amount: Int,
    var pricePerOne: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    var date: LocalDateTime = LocalDateTime.now(),
) {

    override fun toString(): String {
        return "\ndate: ${date.toNiceString()} amount: $amount price per one: ${pricePerOne}€"
    }
}