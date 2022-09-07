package com.example.diplomska.controller

import com.example.diplomska.extensions.getTotalProfit
import com.example.diplomska.extensions.getTotalPurchasePrice
import com.example.diplomska.extensions.getTotalSoldPrice
import com.example.diplomska.model.AppData
import javafx.collections.FXCollections
import javafx.scene.chart.PieChart
import tornadofx.*
import kotlin.math.roundToInt

class StatisticProductProfitController : Controller() {

    val chartDataPurchase = FXCollections.observableArrayList<PieChart.Data>()
    val chartDataSelling = FXCollections.observableArrayList<PieChart.Data>()
    val chartDataProfit = FXCollections.observableArrayList<PieChart.Data>()


    fun setChartData() {
        chartDataPurchase.setAll()
        chartDataProfit.setAll()
        chartDataSelling.setAll()
        val totalPurchase = AppData.products.getTotalPurchasePrice()
        val totalSold = AppData.products.getTotalSoldPrice()
        val totalProfit = AppData.products.getTotalProfit()

        AppData.products.forEach {
            val purchased = (it.getTotalPurchasePrice() * 100).roundToInt() / 100.0
            chartDataPurchase.add(
                PieChart.Data(
                    "${it.name} ${(purchased / totalPurchase * 100 * 100).roundToInt() / 100.0}% ($purchased €)",
                    it.getTotalPurchasePrice()
                )
            )
            val sold = (it.getTotalSoldPrice() * 100).roundToInt() / 100.0
            chartDataSelling.add(
                PieChart.Data(
                    "${it.name} ${(sold / totalSold * 100 * 100).roundToInt() / 100.0}% ($sold €)",
                    it.getTotalSoldPrice()
                )
            )
            val profit = (it.getProfit() * 100).roundToInt() / 100.0
            chartDataProfit.add(
                PieChart.Data(
                    "${it.name} ${(profit / totalProfit * 100 * 100).roundToInt() / 100.0}% ($profit €)",
                    it.getProfit()
                )
            )
        }
    }


//    fun setChartDataBetweenDates(dateStart: LocalDate, dateEnd: LocalDate) {
//        val invoicesBetweenDates =
//            InvoiceDatabase.getBetweenDates(
//                LocalDateTime.of(dateStart, LocalTime.of(0, 0)),
//                LocalDateTime.of(dateEnd, LocalTime.of(23, 59))
//            )
//        chartData.setAll()
//        totalAmountProperty.set("Total: ${invoicesBetweenDates.size}")
//        AppData.employees.forEach {
//            val amount = getNumberInvoicesForUserBetweenDates(it, invoicesBetweenDates).toDouble()
//            chartData.add(
//                PieChart.Data(
//                    "${it.username} $amount (${amount / invoicesBetweenDates.size * 100}%)",
//                    amount
//                )
//            )
//        }
//    }
}