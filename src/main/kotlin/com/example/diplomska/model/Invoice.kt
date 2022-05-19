package com.example.diplomska.model

import java.time.LocalDateTime
import java.util.*


class Invoice(
    var _id: String?,
    var date: LocalDateTime,
    var products: ArrayList<InvoiceItem>,
    var totalPrice: Double,
    var seller: User
){
}
