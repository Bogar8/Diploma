package com.example.diplomska.controller

import com.example.diplomska.extensions.getTotalProfit
import com.example.diplomska.model.AppData
import javafx.collections.FXCollections
import javafx.scene.chart.PieChart
import javafx.scene.chart.XYChart
import tornadofx.*
import kotlin.math.roundToInt

class StatisticProductProfitController : Controller() {

    val chartDataPurchase = FXCollections.observableArrayList<XYChart.Data<String, Number>>()
    val chartDataSelling = FXCollections.observableArrayList<XYChart.Data<String, Number>>()
    val chartDataProfit = FXCollections.observableArrayList<PieChart.Data>()
    val productNameList = FXCollections.observableArrayList<String>()

    fun setAllProductNameList() {
        productNameList.setAll()
        AppData.products.forEach {
            productNameList.add(it.name)
        }
    }

    fun setChartData() {
        setAllProductNameList()
        chartDataPurchase.setAll()
        chartDataProfit.setAll()
        chartDataSelling.setAll()
        val totalProfit = AppData.products.getTotalProfit()
        AppData.products.forEach {
            chartDataPurchase.add(
                XYChart.Data<String, Number>(
                    it.name,
                    it.getTotalPurchasePrice()
                )
            )
            chartDataSelling.add(
                XYChart.Data<String, Number>(
                    it.name,
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