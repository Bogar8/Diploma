package com.example.diplomska.controller

import com.example.diplomska.model.AppData
import com.example.diplomska.model.Invoice
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class InvoiceHistoryController : Controller() {
    var invoices: ObservableList<Invoice> = FXCollections.observableArrayList<Invoice>()
    var selectedInvoice = Invoice()
    var totalAmountOfInvoices = SimpleStringProperty("Total: ")

    fun setInvoices() {
        invoices.setAll(AppData.invoices)
        invoices.sortByDescending { it.date }
        totalAmountOfInvoices.set("Total: ${invoices.size}")
    }
}