package com.example.demo.model

import java.util.*


class Invoice(
    var _id: String?,
    var date: Date,
    var products: ArrayList<InvoiceItem>,
    var totalPrice: Double,
    var seller: User
){
}
