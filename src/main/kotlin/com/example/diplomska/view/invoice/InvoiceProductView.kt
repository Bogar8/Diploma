package com.example.diplomska.view.invoice

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.InvoiceProductController
import com.example.diplomska.model.InvoiceItem
import tornadofx.*

class InvoiceProductView : Fragment("Products of invoice") {
    val controller: InvoiceProductController by inject()
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
                    center = label("Invoice")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                borderpane {
                    center = label(controller.invoiceId)
                    addClass(Styles.whiteBorder)
                }
                tableview(controller.products) {
                    prefWidth = 1200.0
                    prefHeight = 800.0
                    items.onChange {
                        this.refresh()
                        this.requestResize()
                    }
                    columnResizePolicy = SmartResize.POLICY
                    column("Product", InvoiceItem::productNameProperty)
                    column("Amount", InvoiceItem::amountProperty)
                    column("Price per one", InvoiceItem::pricePerOneProperty)
                    column("Total price", InvoiceItem::totalPriceProperty).remainingWidth()
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
    }
}
