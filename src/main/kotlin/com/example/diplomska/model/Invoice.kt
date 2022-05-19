package com.example.diplomska.model

import com.example.diplomska.util.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Serializable
class Invoice(
    var _id: String?,
    var totalPrice: Double,
    var seller: User,
    @Serializable(with = LocalDateTimeSerializer::class)
    var date: LocalDateTime = LocalDateTime.now(),
    var products: ArrayList<InvoiceItem> = ArrayList()
){
}
