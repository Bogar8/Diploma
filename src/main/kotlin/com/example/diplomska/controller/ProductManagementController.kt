package com.example.diplomska.controller

import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import tornadofx.*

class ProductManagementController : Controller() {

    var data = AppData.products

    fun addProduct(product: Product) {
        AppData.products.add(product)
        log.info("Product added, total products ${AppData.products.size}")
    }
}