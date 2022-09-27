package com.example.diplomska.view.invoice

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.InvoiceHistoryController
import com.example.diplomska.controller.InvoiceProductController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Invoice
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import java.io.File
import java.time.LocalDate
import kotlin.math.roundToInt

class InvoiceHistory : View("Invoice history") {
    val controller: InvoiceHistoryController by inject()
    val invoiceProductController: InvoiceProductController by inject()
    val datePropertyStart = SimpleObjectProperty<LocalDate>()
    val datePropertyEnd = SimpleObjectProperty<LocalDate>()

    override val root = borderpane {
        addClass(Styles.background)
        center = hbox {
            prefHeight = 1080.0
            prefWidth = 1920.0
            vbox {
                prefHeight = 1080.0
                prefWidth = 1920.0
                borderpane {
                    center = label("Invoice history")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                tableview(controller.invoices) {
                    placeholder = Label("No invoices found")
                    prefHeight = 1080.0
                    prefWidth = 1920.0
                    columnResizePolicy = SmartResize.POLICY
                    items.onChange {
                        this.refresh()
                        this.requestResize()
                    }
                    column("ID", Invoice::_idProperty)
                    column("Seller", Invoice::sellerProperty).cellFormat {
                        textProperty().set("${it.name} ${it.surname} (${it.username})")
                    }
                    column("Amount of products", Int::class) {
                        value { it.value.getNumberOfItemsInInvoice() }
                    }
                    column("Total price", Invoice::totalPriceProperty)
                    column("Average price per product", Double::class) {
                        value { (it.value.totalPrice / it.value.getNumberOfItemsInInvoice() * 100).roundToInt() / 100.0 }
                    }
                    column("Last changed", Invoice::dateProperty).cellFormat {
                        textProperty().set(it.toNiceString())
                    }
                    onUserSelect(1) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedInvoice = selectionModel.selectedItem
                        }
                    }
                    onUserSelect(2) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedInvoice = selectionModel.selectedItem
                            invoiceProductController.setProducts(controller.selectedInvoice)
                            find<InvoiceProductView>().openWindow(modality = Modality.APPLICATION_MODAL)
                        }
                    }
                }
            }
        }
        right = vbox {
            prefWidth = 200.0
            label("Total amount") {
                textProperty().bind(controller.totalAmountOfInvoices)
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
                        controller.setInvoicesDataBetweenDates(datePropertyStart.value, datePropertyEnd.value)
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
                    controller.setInvoices()
                    controller.selectedInvoice = Invoice()
                }
            }
            button("Open invoice") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/open.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (controller.selectedInvoice._id != "") {
                        invoiceProductController.setProducts(controller.selectedInvoice)
                        find<InvoiceProductView>().openWindow(modality = Modality.APPLICATION_MODAL)
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Select invoice",
                            "You have to select invoice first!",
                            ButtonType.OK,
                        )
                    }
                }
            }
        }
    }
}

