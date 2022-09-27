package com.example.diplomska.view.selling

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.SellingController
import com.example.diplomska.model.Product
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.control.TableView
import tornadofx.*
import java.io.File


class SellingView : View("Selling") {
    private val controller: SellingController by inject()
    private var table: TableView<Product> = TableView<Product>()
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
                table = tableview(controller.filteredProducts) {
                    items.onChange {
                        this.refresh()
                        this.requestResize()
                    }
                    placeholder = Label("No products found")
                    prefWidth = 1920.0
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
                textProperty().addListener { _, _, newValue ->
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
        }
    }


    private fun addProduct() {
        if (controller.selectedProduct._id != "") {
            controller.addProductToBasket()
            table.requestFocus()
            table.selectionModel.select(controller.selectedProduct)
            table.selectionModel.focus(table.selectionModel.selectedIndex)
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

