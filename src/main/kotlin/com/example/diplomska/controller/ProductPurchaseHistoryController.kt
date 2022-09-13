package com.example.diplomska.controller

import com.example.diplomska.model.Product
import com.example.diplomska.model.ProductStock
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class ProductPurchaseHistoryController : Controller() {
    val productStocks: ObservableList<ProductStock> = FXCollections.observableArrayList()
    val productName: SimpleStringProperty = SimpleStringProperty("Product: ")
    fun setProducts(product: Product) {
        productStocks.setAll(product.purchaseHistory)
        productStocks.sortByDescending { it.date }
        log.info("Product stock history for ${product.name} has been set")
        productName.set("Product: ${product.name}")
    }
}