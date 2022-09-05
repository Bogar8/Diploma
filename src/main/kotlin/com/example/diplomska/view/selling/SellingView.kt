package com.example.diplomska.view.selling

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.SellingController
import com.example.diplomska.model.InvoiceItem
import com.example.diplomska.model.Product
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import java.io.File


class SellingView : View("Selling") {
    private val controller: SellingController by inject()
    override val root = borderpane {
        addClass(Styles.background)
        prefHeight = 1080.0
        center = hbox {
            vbox {
                prefHeight = 900.0
                borderpane {
                    center = label("Product list")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                tableview(controller.filteredProducts) {
                    placeholder = Label("No products found")
                    prefWidth = 600.0
                    prefHeight = 1080.0
                    columnResizePolicy = SmartResize.POLICY
                    column("Barcode", Product::barcodeProperty)
                    column("Name", Product::nameProperty).remainingWidth()
                    column("Category", Product::categoryProperty)
                    column("Stock", Product::stockProperty)
                    column("Current sell price", Product::sellingPriceProperty)
                    onUserSelect(1) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedProduct = selectionModel.selectedItem
                        }
                    }
                    onUserSelect(2) {
                        addProduct()
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
            vbox {
                prefHeight = 1080.0
                borderpane {
                    center = label("Basket")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                tableview(controller.basket) {
                    placeholder = Label("No products in basket")
                    prefWidth = 600.0
                    prefHeight = 1080.0
                    columnResizePolicy = SmartResize.POLICY
                    column("Name", InvoiceItem::productNameProperty).remainingWidth()
                    column("Amount", InvoiceItem::amountProperty)
                    column("Price per one", InvoiceItem::pricePerOneProperty)
                    column("Total price", InvoiceItem::totalPriceProperty)
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
            label("Search by barcode or name")
            textfield {
                textProperty().addListener { observable, oldValue, newValue ->
                    controller.setFilteredData(newValue.lowercase())
                }
            }
            label("")
            button("Add product") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/add-cart.png").toURI().toString()
                ) {
                    this.fitHeight = 40.0
                    this.fitWidth = 40.0
                }
                action {
                    addProduct()
                }
            }
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
//                graphic = imageview(
//                    File("src/main/kotlin/com/example/diplomska/assets/invoice.png").toURI().toString()
//                ) {
//                    this.fitHeight = 35.0
//                    this.fitWidth = 35.0
//                }
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

    private fun addProduct() {
        if (controller.selectedProduct._id != "")
            controller.addProductToBasket()
        else {
            alert(
                Alert.AlertType.ERROR,
                "Select product",
                "You have to select product first!",
                ButtonType.OK,
            )
        }
    }
}

