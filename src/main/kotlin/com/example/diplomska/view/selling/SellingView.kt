package com.example.diplomska.view.selling

import com.example.diplomska.controller.SellingController
import com.example.diplomska.model.InvoiceItem
import com.example.diplomska.model.Product
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*


class SellingView : View("My View") {
    private val controller: SellingController by inject()

    override val root = borderpane {
        center = hbox {
            prefHeight = 900.0
            vbox {
                prefHeight = 900.0
                label("Product list")
                tableview(controller.filteredProducts) {
                    prefWidth = 600.0
                    prefHeight = 900.0
                    column("Barcode", Product::barcodeProperty)
                    column("Name", Product::nameProperty)
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
                prefHeight = 900.0
                label("Basket")
                tableview(controller.basket) {
                    prefWidth = 600.0
                    prefHeight = 900.0
                    column("Name", InvoiceItem::productNameProperty)
                    column("Amount", InvoiceItem::amountProperty)
                    column("Price per one", InvoiceItem::pricePerOneProperty)
                    column("Total price", InvoiceItem::totalPriceProperty)
                    onUserSelect(1) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedInvoiceItem = selectionModel.selectedItem
                        }
                    }
                    onUserSelect(2) {
                        val selected = selectionModel.selectedIndex
                        removeProduct()
                        selectionModel.select(selected)
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
        right = vbox {
            prefWidth = 200.0
            label("Search by barcode or name")
            textfield {
                textProperty().addListener { observable, oldValue, newValue ->
                    controller.setFilteredData(newValue.lowercase())
                }
            }
            label("")
            button("Add product to basket") {
                useMaxWidth = true
                action {
                    addProduct()
                }
            }
            button("Remove product from basket") {
                useMaxWidth = true
                action {
                    removeProduct()
                }
            }
            button("Done") {
                useMaxWidth = true
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

