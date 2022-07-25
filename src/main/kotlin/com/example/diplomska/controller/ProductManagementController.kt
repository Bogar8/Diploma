package com.example.diplomska.controller

import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import tornadofx.*

class ProductManagementController : Controller() {

    val products = AppData.products.asObservable()
    fun addProduct(product: Product) {
        products.add(product)
        log.info("Product added, total products ${AppData.products.size}")
    }
}