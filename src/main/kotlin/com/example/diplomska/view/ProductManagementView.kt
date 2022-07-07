package com.example.diplomska.view

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.AppData
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import tornadofx.*


class ProductManagementView : View("My View") {
    val controller: ProductManagementController by inject()
    override val root = vbox {

        button("Add product") {
            useMaxWidth = true
            action {
                runAsync {
                    controller.addProduct(Product("21", 123, "novoIme", Category.FOOD, 100, null, false))
                }
            }
        }

        tableview(AppData.products.asObservable()) {
            readonlyColumn("Barcode", Product::barcode)
            readonlyColumn("Name", Product::name)
            readonlyColumn("Category", Product::category)
            readonlyColumn("Stock", Product::stock)
            readonlyColumn("Last changed", Product::lastChanged).cellFormat {
                text = it.toNiceString()
            }
            readonlyColumn("Current sell price", Product::sellingHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
            readonlyColumn("Current purchase price", Product::purchaseHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
            readonlyColumn("Active", Product::isActive).cellFormat {
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
        }

        button("Back") {
            useMaxWidth = true
            action {
                replaceWith<MainView>()
            }
        }
    }

}
