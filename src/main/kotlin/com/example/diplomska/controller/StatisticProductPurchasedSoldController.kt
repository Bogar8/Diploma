package com.example.diplomska.controller

import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.AppData
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import tornadofx.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt

class StatisticProductPurchasedSoldController : Controller() {

    val chartDataPurchased: ObservableList<XYChart.Data<String, Number>> = FXCollections.observableArrayList()
    val chartDataSelling: ObservableList<XYChart.Data<String, Number>> = FXCollections.observableArrayList()
    val productNameList: ObservableList<String> = FXCollections.observableArrayList()

    private fun setAllProductNameList() {
        productNameList.setAll()
        AppData.products.forEach {
            if (it.getTotalPurchasedAmount() > 0 || it.getTotalSoldAmount() > 0) {
                productNameList.add(it.name)
            }
        }
    }

    fun setChartData() {
        setAllProductNameList()
        chartDataPurchased.setAll()
        chartDataSelling.setAll()
        AppData.products.forEach {
            if (it.getTotalPurchasedAmount() > 0 || it.getTotalSoldAmount() > 0) {
                chartDataPurchased.add(
                    XYChart.Data<String, Number>(
                        it.name,
                        (it.getTotalPurchasedPrice() * 100).roundToInt() / 100.0
                    )
                )
                chartDataSelling.add(
                    XYChart.Data<String, Number>(
                        it.name,
                        (it.getTotalSoldPrice() * 100).roundToInt() / 100.0
                    )
                )
            }
        }
        log.info("Product purchased / sold chart data has been set")
    }


    fun setChartDataBetweenDates(dateStart: LocalDate, dateEnd: LocalDate) {
        setAllProductNameList()
        chartDataPurchased.setAll()
        chartDataSelling.setAll()
        val dateStartTime = LocalDateTime.of(dateStart.year, dateStart.month, dateStart.dayOfMonth, 0, 0)
        val dateEndTime = LocalDateTime.of(dateEnd.year, dateEnd.month, dateEnd.dayOfMonth, 23, 59)
        AppData.products.forEach {
            if (it.getTotalPurchasedAmount() > 0 || it.getTotalSoldAmount() > 0) {
                chartDataPurchased.add(
                    XYChart.Data<String, Number>(
                        it.name,
                        (it.getTotalPurchasedPriceBetweenDates(dateStartTime, dateEndTime) * 100).roundToInt() / 100.0
                    )
                )
                chartDataSelling.add(
                    XYChart.Data<String, Number>(
                        it.name,
                        (it.getTotalSoldPriceBetweenDates(dateStartTime, dateEndTime) * 100).roundToInt() / 100.0
                    )
                )
            }
        }
        log.info("Product purchased / sold chart data has been set between ${dateStartTime.toNiceString()} and ${dateEndTime.toNiceString()}")
    }
}