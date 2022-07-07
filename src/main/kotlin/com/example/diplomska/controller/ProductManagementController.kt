package com.example.diplomska.controller

import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import tornadofx.Controller

class ProductManagementController : Controller() {

    fun addProduct(product: Product){
        AppData.products.add(product)
        log.info("Product added, total products ${AppData.products.size}")
    }
}