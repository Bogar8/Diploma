package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.model.*
import javafx.collections.FXCollections
import tornadofx.*

class SellingController : Controller() {
    var errorMessage: String = ""
    var products = FXCollections.observableArrayList<Product>()
    var basket = FXCollections.observableArrayList<InvoiceItem>()
    var productsInBasket =
        HashMap<Product, Int>() // keeping amount of each product in basket for updating stock and purchase history
    var selectedProduct = Product()
    var selectedInvoiceItem = InvoiceItem()

    fun refreshData() {
        val data = AppData.products.filter { it.isActive && it.stock > 0 }
        products.setAll(data)
    }

    fun addProductToBasket(): Boolean {
        val item = hasItem(selectedProduct.name)
        var amount = 0
        if (item != null) {
            amount = item.amount
            if (selectedProduct.stock < amount + 1)
                return false
            basket.remove(item)
        }
        productsInBasket[selectedProduct] = amount + 1
        val newItem = InvoiceItem(selectedProduct.name, amount + 1, selectedProduct.sellingPrice)
        basket.add(newItem)
        log.info { "Product ${newItem.productName} successfully added to basket" }
        return true
    }

    fun removeFromBasket() {
        val item = hasItem(selectedInvoiceItem.productName)
        if (item != null) {
            val amount = item.amount
            basket.remove(item)
            productsInBasket[selectedProduct] = amount - 1
            log.info { "Product ${item.productName} successfully removed from basket" }
            if (amount - 1 > 0) {
                val newItem = InvoiceItem(item.productName, amount - 1, item.pricePerOne)
                basket.add(newItem)
            } else {
                selectedInvoiceItem = InvoiceItem()
            }
        }
    }

    fun printInvoice() {
        val invoice = Invoice()
        invoice.seller = AppData.loggedInUser
        basket.forEach {
            invoice.products.add(it)
            invoice.totalPrice += it.totalPrice
        }
        log.info { "Invoice created by seller ${invoice.seller.name} ${invoice.seller.surname} for ${invoice.products.size} products." }
        InvoiceDatabase.insert(invoice)
        updateProductStockAndSavePurchaseHistory()
        productsInBasket = HashMap<Product, Int>()
        basket.setAll()
    }

    private fun hasItem(name: String): InvoiceItem? {
        basket.forEach {
            if (it.productName == name)
                return it
        }
        return null
    }

    private fun updateProductStockAndSavePurchaseHistory() {
        productsInBasket.forEach {
            if (it.value > 0) {
                it.key.sellingHistory.add(ProductStock(it.value, it.key.sellingPrice))
                it.key.stock -= it.value
                ProductDatabase.update(it.key)
            }
        }
    }

}
