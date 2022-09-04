package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.model.Product
import tornadofx.*


class MainController : Controller() {

    fun getOutOfStockProducts(): ArrayList<Product> {
        return ProductDatabase.getOutOfStock()
    }

}