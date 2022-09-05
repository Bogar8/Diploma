package com.example.diplomska.controller

import com.example.diplomska.model.Invoice
import com.example.diplomska.model.InvoiceItem
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class InvoiceProductController : Controller() {
    val products: ObservableList<InvoiceItem> = FXCollections.observableArrayList<InvoiceItem>()

    fun setProducts(invoice: Invoice) {
        products.setAll(invoice.products)
    }
}