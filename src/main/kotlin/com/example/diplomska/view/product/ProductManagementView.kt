package com.example.diplomska.view.product

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.controller.ProductPurchaseHistoryController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Product
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import java.io.File

//TODO: FILTER SEARCH textfield with combobox for each field or just by name?
class ProductManagementView : View("Products managment") {
    private val controller: ProductManagementController by inject()
    private val purchaseHistoryController: ProductPurchaseHistoryController by inject()

    override val root = borderpane {
        addClass(Styles.background)
        center = hbox {
            prefHeight = 1080.0
            prefWidth = 1920.0
            vbox {
                prefHeight = 1080.0
                prefWidth = 1920.0
                borderpane {
                    center = label("Product administration")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                tableview(controller.filteredProducts) {
                    items.onChange {
                        this.refresh()
                        this.requestResize()
                    }
                    placeholder = Label("No products found")
                    prefHeight = 1080.0
                    columnResizePolicy = SmartResize.POLICY
                    column("Barcode", Product::barcodeProperty)
                    column("Name", Product::nameProperty)
                    column("Category", Product::categoryProperty)
                    column("Stock", Product::stockProperty).cellFormat {
                        textProperty().set(it.toString())
                        style {
                            if (it.toInt() in 1..9) {
                                backgroundColor += c("FFFF33") //yellow
                            }
                            if (it.toInt() == 0) {
                                backgroundColor += c("FF6863") //red
                            }
                        }
                    }
                    column("Current sell price in €", Product::sellingPriceProperty)
                    column("Current purchase price in €", Product::lastPurchasedPriceProperty).cellFormat {
                        if (it.toDouble() > 0)
                            textProperty().set(it.toString())
                        else
                            textProperty().set("no data")

                    }
                    column("Active", Product::isActiveProperty).cellFormat {
                        if (it) {
                            text = "Yes"
                        } else {
                            text = "No"
                        }
                        style {
                            if (it) {
                                backgroundColor += c("24B61A")
                            } else {
                                backgroundColor += c("FF6863")
                            }
                        }
                    }
                    column("Last changed", Product::lastChangedProperty).cellFormat {
                        textProperty().set(it.toNiceString())
                    }
                    onUserSelect(1) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedProduct = selectionModel.selectedItem
                        }
                    }

                    onUserSelect(2) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedProduct = selectionModel.selectedItem
                            purchaseHistoryController.setProducts(controller.selectedProduct)
                            find<ProductPurchaseHistory>().openWindow(modality = Modality.APPLICATION_MODAL)
                        }
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
        right = vbox {
            prefWidth = 200.0
            label("Search by barcode or name")
            textfield {
                textProperty().addListener { _, _, newValue ->
                    controller.setFilteredData(newValue.lowercase())
                }
            }
            label("")
            button("Add product") {
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/add.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                useMaxWidth = true
                action {
                    find<ProductAddView>().openWindow(modality = Modality.APPLICATION_MODAL)
                }
            }
            button("Edit product") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/edit.png").toURI().toString()
                ) {
                    this.fitHeight = 30.0
                    this.fitWidth = 30.0
                }
                action {
                    if (controller.selectedProduct._id != "") {
                        find<ProductEditView>().openWindow(modality = Modality.APPLICATION_MODAL)
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
            button("Add stock") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/stock.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (controller.selectedProduct._id != "") {
                        find<ProductAddStockView>().openWindow(modality = Modality.APPLICATION_MODAL)
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
            button("Purchase history") {
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/history.png").toURI().toString()
                ) {
                    this.fitHeight = 40.0
                    this.fitWidth = 40.0
                }
                useMaxWidth = true
                action {
                    purchaseHistoryController.setProducts(controller.selectedProduct)
                    find<ProductPurchaseHistory>().openWindow(modality = Modality.APPLICATION_MODAL)
                }
            }
            button("Delete product") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/remove.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    if (controller.selectedProduct._id != "") {
                        alert(
                            Alert.AlertType.CONFIRMATION,
                            "Deleting product",
                            "Are you sure you want to delete product ${controller.selectedProduct.name}",
                            ButtonType.YES,
                            ButtonType.NO,
                            actionFn = { btnType ->
                                if (btnType.buttonData == ButtonBar.ButtonData.YES) {
                                    if (controller.deleteProduct(controller.selectedProduct)) {
                                        alert(
                                            Alert.AlertType.INFORMATION,
                                            "Deleting product",
                                            "Product ${controller.selectedProduct.name} successfully deleted",
                                            ButtonType.OK,
                                        )
                                        controller.selectedProduct = Product()
                                    } else {
                                        alert(
                                            Alert.AlertType.ERROR,
                                            "Deleting product",
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


