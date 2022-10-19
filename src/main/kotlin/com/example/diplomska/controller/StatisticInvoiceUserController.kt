package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.Invoice
import com.example.diplomska.model.User
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt

class StatisticInvoiceUserController : Controller() {

    var chartData: ObservableList<PieChart.Data> = FXCollections.observableArrayList<PieChart.Data>()
    var totalAmountProperty = SimpleStringProperty("Total: ")

    private fun getNumberInvoicesForUser(user: User): Int {
        return AppData.invoices.filter { it.seller._id == user._id }.size
    }

    private fun getNumberInvoicesForUserBetweenDates(user: User, data: ArrayList<Invoice>): Int {
        return data.filter { it.seller._id == user._id }.size
    }

    fun setChartData() {
        chartData.setAll()
        totalAmountProperty.set("Total: ${AppData.invoices.size}")
        AppData.employees.forEach {
            val amount = getNumberInvoicesForUser(it).toDouble()
            if (amount > 0) {
                val percent = (amount / AppData.invoices.size * 100 * 100).roundToInt() / 100.0
                chartData.add(
                    PieChart.Data(
                        "${it.username} ${amount.toInt()} " + "($percent%)",
                        amount
                    )
                )
            }
        }
        log.info("Invoice user chart data has been set")
    }

    fun setChartDataBetweenDates(dateStart: LocalDate, dateEnd: LocalDate) {
        val invoicesBetweenDates =
            InvoiceDatabase.getBetweenDates(
                LocalDateTime.of(dateStart, LocalTime.of(0, 0)),
                LocalDateTime.of(dateEnd, LocalTime.of(23, 59))
            )
        chartData.setAll()
        totalAmountProperty.set("Total: ${invoicesBetweenDates.size}")
        AppData.employees.forEach {
            val amount = getNumberInvoicesForUserBetweenDates(it, invoicesBetweenDates).toDouble()
            if (amount > 0) {
                chartData.add(
                    PieChart.Data(
                        "${it.username} ${amount.toInt()} (${(amount / invoicesBetweenDates.size * 100 * 100).roundToInt() / 100.0}%)",
                        amount
                    )
                )
            }
        }

        log.info("Invoice user chart data has been set between $dateStart and $dateEnd")
    }
}