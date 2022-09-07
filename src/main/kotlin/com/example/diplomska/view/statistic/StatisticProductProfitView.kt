package com.example.diplomska.view.statistic

import com.example.diplomska.controller.StatisticProductProfitController
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.File
import java.time.LocalDate

class StatisticProductProfitView : View("Invoice per user statistic") {
    private val controller: StatisticProductProfitController by inject()
    val datePropertyStart = SimpleObjectProperty<LocalDate>()
    val datePropertyEnd = SimpleObjectProperty<LocalDate>()

    override val root = borderpane {
        prefHeight = 1080.0
        useMaxWidth = true
        center = hbox {
            //barchart sometimes bugs out and needs to be refreshed
            barchart("Product purchase/sell", CategoryAxis(controller.productNameList), NumberAxis()) {
                series("Purchase", controller.chartDataPurchase)
                series("Sell", controller.chartDataSelling)
                hgrow = Priority.ALWAYS
            }
            piechart("Product profit", controller.chartDataProfit) {
                hgrow = Priority.ALWAYS
            }
        }

        right = vbox {
            label("Date from")
            datepicker(datePropertyStart) {
                value = LocalDate.now()
            }
            label("Date to")
            datepicker(datePropertyEnd) {
                value = LocalDate.now()
            }
            button("Select date") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/calculator.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (datePropertyStart.value <= datePropertyEnd.value) {
                        controller.setChartDataBetweenDates(datePropertyStart.value, datePropertyEnd.value)
                    }
                    else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Date error",
                            "Start date has to be before end date",
                            ButtonType.OK,
                        )
                    }
                }
            }
            button("Reset date filter") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/undo-arrow.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    datePropertyStart.set(LocalDate.now())
                    datePropertyEnd.set(LocalDate.now())
                    controller.setChartData()
                }
           }
        }
    }
}
