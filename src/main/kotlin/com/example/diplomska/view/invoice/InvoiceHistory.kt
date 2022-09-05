package com.example.diplomska.view.invoice

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.InvoiceHistoryController
import com.example.diplomska.controller.InvoiceProductController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Invoice
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import kotlin.math.roundToInt

class InvoiceHistory : View("Invoice history") {
    val controller: InvoiceHistoryController by inject()
    val invoiceProductController: InvoiceProductController by inject()

    override val root = borderpane {
        addClass(Styles.background)
        center = tableview(controller.invoices) {
            placeholder = Label("No invoices found")
            prefHeight = 1080.0
            columnResizePolicy = SmartResize.POLICY
            column("Seller", Invoice::sellerProperty).cellFormat {
                text = "${it.name} ${it.surname} (${it.username})"
            }
            column("Amount of products", Int::class) {
                value { it.value.getNumberOfItemsInInvoice() }
            }
            column("Total price", Invoice::totalPriceProperty)
            column("Average price per product", Double::class) {
                value { (it.value.totalPrice / it.value.getNumberOfItemsInInvoice() * 100).roundToInt() / 100.0 }
            }
            column("Last changed", Invoice::dateProperty).remainingWidth().cellFormat {
                text = it.toNiceString()
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
            columnResizePolicy = SmartResize.POLICY
        }
        right = vbox {
            prefWidth = 200.0
            label("Total amount") {
                textProperty().bind(controller.totalAmountOfInvoices)
                addClass(Styles.totalPrice)
            }
            button("Open invoice") {
                useMaxWidth = true
//                graphic = imageview(
//                    File("src/main/kotlin/com/example/diplomska/assets/edit.png").toURI().toString()
//                ) {
//                    this.fitHeight = 30.0
//                    this.fitWidth = 30.0
//                }
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

