package com.example.diplomska.view.statistic

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.StatisticInvoiceUserController
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
import java.io.File
import java.time.LocalDate

class StatisticInvoiceUserView : View("Invoice per user statistic") {
    private val statisticController: StatisticInvoiceUserController by inject()
    val datePropertyStart = SimpleObjectProperty<LocalDate>()
    val datePropertyEnd = SimpleObjectProperty<LocalDate>()
    override val root = borderpane {
        prefHeight = 1080.0
        center = piechart("Invoices made by users", statisticController.chartData) {
            style {
                addClass(Styles.backgroundLabelSecondary)
            }
        }
        right = vbox {
            label("Total amount") {
                textProperty().bind(statisticController.totalAmountProperty)
                addClass(Styles.totalPrice)
            }
            label()
            label("Date from")
            datepicker(datePropertyStart) {
                value = LocalDate.now()
            }
            label("Date to")
            datepicker(datePropertyEnd) {
                value = LocalDate.now()
            }
            label()
            button("Select date") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/calculator.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (datePropertyStart.value <= datePropertyEnd.value)
                        statisticController.setChartDataBetweenDates(datePropertyStart.value, datePropertyEnd.value)
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
                    statisticController.setChartData()
                }
            }
        }
    }
}
