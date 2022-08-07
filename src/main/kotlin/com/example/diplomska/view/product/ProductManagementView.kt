package com.example.diplomska.view.product

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Product
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*


class ProductManagementView : View("My View") {
    private val controller: ProductManagementController by inject()

    override val root = borderpane {
        center = tableview(controller.products) {
            prefHeight = 900.0
            column("Barcode", Product::barcodeProperty)
            column("Name", Product::nameProperty)
            column("Category", Product::categoryProperty)
            column("Stock", Product::stockProperty)
            column("Last changed", Product::lastChangedProperty).cellFormat {
                text = it.toNiceString()
            }
            column("Current sell price", Product::sellingPriceProperty)
            column("Current purchase price", Product::purchaseHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
            column("Active", Product::isActiveProperty).cellFormat {
                if (it) {
                    text = "Yes"
                } else {
                    text = "No"
                }
                style {
                    if (it) {
                        backgroundColor += c("#00FF00")
                    } else {
                        backgroundColor += c("#FF0000")
                    }
                }
            }
            setOnMouseClicked {
                if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                    controller.selectedProduct = selectionModel.selectedItem
                }
            }
            columnResizePolicy = SmartResize.POLICY
        }
        right = vbox {
            prefWidth = 200.0
            button("Add product") {
                useMaxWidth = true
                action {
                    find<ProductAddView>().openWindow()
                }
            }
            button("Edit product") {
                useMaxWidth = true
                action {
                    if (controller.selectedProduct._id != "") {
                        find<ProductEditView>().openWindow()
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Select product",
                            "You have to select product first!",
                            ButtonType.OK,
                        )
                    }
                }
            }
            button("Delete product") {
                useMaxWidth = true
                action {
                    if (controller.selectedProduct._id != "") {
                        alert(
                            Alert.AlertType.CONFIRMATION,
                            "Deleting user",
                            "Are you sure you want to delete product ${controller.selectedProduct.name}",
                            ButtonType.YES,
                            ButtonType.NO,
                            actionFn = { btnType ->
                                if (btnType.buttonData == ButtonBar.ButtonData.YES) {
                                    if (controller.deleteProduct(controller.selectedProduct)) {
                                        alert(
                                            Alert.AlertType.INFORMATION,
                                            "Deleting user",
                                            "Product ${controller.selectedProduct.name} successfully deleted",
                                            ButtonType.OK,
                                        )
                                        controller.selectedProduct = Product()
                                    } else {
                                        alert(
                                            Alert.AlertType.ERROR,
                                            "Deleting user",
                                            "Error occurred when deleting product ${controller.selectedProduct.name}",
                                            ButtonType.OK,
                                        )
                                    }
                                }
                            })
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Select product",
                            "You have to select product first!",
                            ButtonType.OK,
                        )
                    }
                }
            }
        }
    }
}

