package com.example.diplomska.model

import java.time.LocalDate
import java.util.*


class Invoice(
    var _id: String?,
    var date: LocalDate,
    var products: ArrayList<InvoiceItem>,
    var totalPrice: Double,
    var seller: User
){
}
