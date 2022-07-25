package com.example.diplomska.view

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
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
            column("Current sell price", Product::sellingHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
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
            columnResizePolicy = SmartResize.POLICY
        }
        right = vbox {
            prefWidth = 200.0
            button("Add product") {
                useMaxWidth = true
                action {
                    controller.addProduct(Product("21", 123, "novoIme", Category.FOOD, 100, false))
                }
            }
        }
    }

}
