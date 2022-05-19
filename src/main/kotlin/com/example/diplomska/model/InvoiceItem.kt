package com.example.diplomska.model

@kotlinx.serialization.Serializable
class InvoiceItem (
    var productName: String,
    var amount: Int,
    var totalPrice: Double,
    var pricePerOne: Double
){
    override fun toString(): String {
        return "product name:$productName amount:$amount price per one:$pricePerOne total price:$totalPrice"
    }
}