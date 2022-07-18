package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import tornadofx.*
import java.time.LocalDateTime


class Invoice(
    var _id: String,
    var totalPrice: Double,
    var seller: User,
    var date: LocalDateTime = LocalDateTime.now(),
    var products: ArrayList<InvoiceItem> = ArrayList()
) : JsonModel{

    companion object {
        const val DATABASE_NAME = "Invoices"
    }

    override fun toString(): String {
        return "id:$_id total price:$totalPrice seller:$seller date:${date.toNiceString()}\nproducts:$products"
    }
}
