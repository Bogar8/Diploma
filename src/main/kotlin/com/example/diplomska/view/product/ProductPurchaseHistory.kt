package com.example.diplomska.view.product

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.ProductPurchaseHistoryController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.ProductStock
import tornadofx.*
import kotlin.math.roundToInt

class ProductPurchaseHistory : Fragment("Products purchase history") {
    val controller: ProductPurchaseHistoryController by inject()
    override val root = borderpane {
        addClass(Styles.background)
        prefWidth = 1200.0
        prefHeight = 800.0
        center = hbox {
            prefWidth = 1200.0
            prefHeight = 800.0
            vbox {
                prefWidth = 1200.0
                prefHeight = 800.0
                borderpane {
                    center = label("Products purchase history")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                borderpane {
                    center = label(controller.productName)
                    addClass(Styles.whiteBorder)
                }
                tableview(controller.productStocks) {
                    prefHeight = 1080.0
                    columnResizePolicy = SmartResize.POLICY
                    items.onChange {
                        this.requestResize()
                    }
                    column("Amount", ProductStock::amountProperty)
                    column("Price per one", ProductStock::pricePerOneProperty)
                    column("Total", Double::class) {
                        value { (it.value.pricePerOne * it.value.amount * 100).roundToInt() / 100.0 }
                    }
                    column("Date", ProductStock::dateProperty).remainingWidth().cellFormat {
                        text = it.toNiceString()
                    }
                }
            }
        }
    }
}

