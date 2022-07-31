package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.ProductDatabase

import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import tornadofx.*

class ProductManagementController : Controller() {
    var errorMessage: String = ""
    val products = AppData.products.asObservable()
    var selectedProduct = Product()
    fun addProduct(product: Product) : Boolean {
        if (ProductDatabase.insert(product)) {
            products.add(product)
            log.info { "Product ${product.name} successfully added" }
            return true
        } else {
            errorMessage = "Error when trying to add user"
            log.info { errorMessage }
            return false
        }
    }
}