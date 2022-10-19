package com.example.diplomska.view.selling

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.SellingController
import com.example.diplomska.model.InvoiceItem
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import java.io.File


class BasketView : View("Selling") {
    private val controller: SellingController by inject()
    override val root = borderpane {
        addClass(Styles.background)
        prefHeight = 1080.0
        center = vbox {
            vbox {
                prefHeight = 1080.0
                borderpane {
                    center = label("Basket")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                hbox {
                    tableview(controller.basket) {
                        items.onChange {
                            this.refresh()
                            this.requestResize()
                        }
                        placeholder = Label("No products in basket")
                        prefWidth = 1200.0
                        prefHeight = 1080.0
                        columnResizePolicy = SmartResize.POLICY
                        column("Name", InvoiceItem::productNameProperty).remainingWidth()
                        column("Amount", InvoiceItem::amountProperty)
                        column("Price per one in €", InvoiceItem::pricePerOneProperty)
                        column("Total price in €", InvoiceItem::totalPriceProperty)
                        onUserSelect(1) {
                            if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                                controller.selectedInvoiceItem = selectionModel.selectedItem
                            }
                        }
                        onUserSelect(2) {
                            if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                                controller.selectedInvoiceItem = selectionModel.selectedItem
                                setAmount()
                            }
                        }
                        columnResizePolicy = SmartResize.POLICY
                    }

                    vbox {
                        label("Total price") {
                            textProperty().bind(controller.totalPriceStringProperty)
                            addClass(Styles.totalPrice)
                        }
                        style {
                            paddingLeft = 20
                            paddingTop = 20
                        }
                    }
                }
            }
        }
        right = vbox {
            style {
                borderColor += box(
                    top = c(Styles.primaryColor),
                    right = c(Styles.primaryTable),
                    left = c("#FFFFFF"),
                    bottom = c(Styles.primaryTable)
                )
            }
            prefWidth = 200.0
            button("Remove product") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/remove-cart.png").toURI().toString()
                ) {
                    this.fitHeight = 40.0
                    this.fitWidth = 40.0
                }
                action {
                    removeProduct()
                }
            }
            button("Set amount") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/edit.png").toURI().toString()
                ) {
                    this.fitHeight = 40.0
                    this.fitWidth = 40.0
                }
                action {
                    setAmount()
                }
            }
            button("Done") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/invoice.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (controller.basket.size > 0) {
                        controller.printInvoice()
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Empty basket",
                            "You have to have least one item in basket!",
                            ButtonType.OK,
                        )
                    }
                }
            }
        }
    }

    private fun removeProduct() {
        if (controller.selectedInvoiceItem.productName != "") {
            controller.removeFromBasket()
        } else {
            alert(
                Alert.AlertType.ERROR,
                "Select invoice item",
                "You have to select invoice item first!",
                ButtonType.OK,
            )
        }
    }

    private fun setAmount() {
        if (controller.selectedInvoiceItem.productName != "") {
            find<SetAmountView>().openWindow(modality = Modality.APPLICATION_MODAL)
        } else {
            alert(
                Alert.AlertType.ERROR,
                "Select invoice item",
                "You have to select invoice item first!",
                ButtonType.OK,
            )
        }
    }
}

