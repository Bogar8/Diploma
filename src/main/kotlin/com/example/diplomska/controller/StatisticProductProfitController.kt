package com.example.diplomska.controller

import com.example.diplomska.extensions.getTotalProfit
import com.example.diplomska.extensions.getTotalProfitBetweenDates
import com.example.diplomska.model.AppData
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt

class StatisticProductProfitController : Controller() {

    val chartDataProfit: ObservableList<PieChart.Data> = FXCollections.observableArrayList()


    fun setChartData() {
        chartDataProfit.setAll()
        val totalProfit = AppData.products.getTotalProfit()
        AppData.products.forEach {
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
        chartDataProfit.setAll()
        val dateStartTime = LocalDateTime.of(dateStart.year, dateStart.month, dateStart.dayOfMonth, 0, 0)
        val dateEndTime = LocalDateTime.of(dateEnd.year, dateEnd.month, dateEnd.dayOfMonth, 23, 59)
        val totalProfit = AppData.products.getTotalProfitBetweenDates(dateStartTime, dateEndTime)
        AppData.products.forEach {
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