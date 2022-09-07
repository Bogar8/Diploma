package com.example.diplomska.controller

import com.example.diplomska.extensions.getTotalProfit
import com.example.diplomska.extensions.getTotalProfitBetweenDates
import com.example.diplomska.model.AppData
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import javafx.scene.chart.XYChart
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt

class StatisticProductProfitController : Controller() {

    val chartDataPurchase: ObservableList<XYChart.Data<String, Number>> = FXCollections.observableArrayList()
    val chartDataSelling: ObservableList<XYChart.Data<String, Number>> = FXCollections.observableArrayList()
    val chartDataProfit: ObservableList<PieChart.Data> = FXCollections.observableArrayList()
    val productNameList: ObservableList<String> = FXCollections.observableArrayList()

    private fun setAllProductNameList() {
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
                    (it.getTotalPurchasePrice() * 100).toInt() / 100.0
                )
            )
            chartDataSelling.add(
                XYChart.Data<String, Number>(
                    it.name,
                    (it.getTotalSoldPrice() * 100).toInt() / 100.0
                )
            )
            val profit = (it.getProfit() * 100).roundToInt() / 100.0
            chartDataProfit.add(
                PieChart.Data(
                    "${it.name} ${(profit / totalProfit * 100 * 100).roundToInt() / 100.0}% ($profit €)",
                    profit
                )
            )
        }
    }


    fun setChartDataBetweenDates(dateStart: LocalDate, dateEnd: LocalDate) {
        setAllProductNameList()
        chartDataPurchase.setAll()
        chartDataProfit.setAll()
        chartDataSelling.setAll()
        val dateStartTime = LocalDateTime.of(dateStart.year, dateStart.month, dateStart.dayOfMonth, 0, 0)
        val dateEndTime = LocalDateTime.of(dateEnd.year, dateEnd.month, dateEnd.dayOfMonth, 23, 59)
        val totalProfit = AppData.products.getTotalProfitBetweenDates(dateStartTime, dateEndTime)
        AppData.products.forEach {
            chartDataPurchase.add(
                XYChart.Data<String, Number>(
                    it.name,
                    (it.getTotalPurchasePriceBetweenDates(dateStartTime, dateEndTime) * 100).toInt() / 100.0
                )
            )
            chartDataSelling.add(
                XYChart.Data<String, Number>(
                    it.name,
                    (it.getTotalSoldPriceBetweenDates(dateStartTime, dateEndTime) * 100).toInt() / 100.0
                )
            )
            val profit = (it.getProfitBetweenDates(dateStartTime, dateEndTime) * 100).roundToInt() / 100.0
            chartDataProfit.add(
                PieChart.Data(
                    "${it.name} ${(profit / totalProfit * 100 * 100).roundToInt() / 100.0}% ($profit €)",
                    profit
                )
            )
        }
    }
}