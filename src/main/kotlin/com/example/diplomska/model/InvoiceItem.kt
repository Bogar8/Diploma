package com.example.diplomska.model

@kotlinx.serialization.Serializable
class InvoiceItem (
    var productName: String,
    var amount: Int,
    var totalPrice: Double,
    var pricePerOne: Double
)