package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.Invoice
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class InvoiceHistoryController : Controller() {
    var invoices: ObservableList<Invoice> = FXCollections.observableArrayList<Invoice>()
    var selectedInvoice = Invoice()
    var totalAmountOfInvoices = SimpleStringProperty("Total: ")

    fun setInvoices() {
        invoices.setAll(AppData.invoices)
        invoices.sortByDescending { it.date }
        totalAmountOfInvoices.set("Total: ${invoices.size}")
    }

    fun setInvoicesDataBetweenDates(dateStart: LocalDate, dateEnd: LocalDate) {
        invoices.setAll(InvoiceDatabase.getBetweenDates(
            LocalDateTime.of(dateStart, LocalTime.of(0, 0)),
            LocalDateTime.of(dateEnd, LocalTime.of(23, 59))
        ))
        invoices.sortByDescending { it.date }
        totalAmountOfInvoices.set("Total: ${invoices.size}")
    }
}