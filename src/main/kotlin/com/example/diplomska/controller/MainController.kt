package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class MainController : Controller() {
    var userInfo = SimpleStringProperty()

    fun getOutOfStockProducts(): ArrayList<Product> {
        log.info("Out of stock products are checked")
        userInfo.set(AppData.loggedInUser.name + " " + AppData.loggedInUser.surname + " (" + AppData.loggedInUser.username + ")")
        return ProductDatabase.getOutOfStock()
    }

}