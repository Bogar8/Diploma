package com.example.diplomska.controller

import com.example.diplomska.model.Invoice
import com.example.diplomska.model.InvoiceItem
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class InvoiceProductController : Controller() {
    val products: ObservableList<InvoiceItem> = FXCollections.observableArrayList<InvoiceItem>()
    val invoiceId: SimpleStringProperty = SimpleStringProperty("Invoice id: ")
    fun setProducts(invoice: Invoice) {
        products.setAll(invoice.products)
        invoiceId.set("Invoice id: ${invoice._id}")
        log.info("Products of invoice have been set")
    }
}