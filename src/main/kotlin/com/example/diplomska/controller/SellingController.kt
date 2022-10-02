package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.model.*
import com.example.diplomska.view.MainView
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import kotlin.math.roundToInt

class SellingController : Controller() {
    var products: ObservableList<Product> = FXCollections.observableArrayList<Product>(AppData.products)
    var filteredProducts: ObservableList<Product> = FXCollections.observableArrayList<Product>(products)
    var basket: ObservableList<InvoiceItem> = FXCollections.observableArrayList<InvoiceItem>()
    var productsInBasket =
        HashMap<Product, Int>() // keeping amount of each product in basket for updating stock and purchase history
    var selectedProduct = Product()
    var selectedInvoiceItem = InvoiceItem()
    var filterInUse: String = ""
    var totalPrice = 0.0
    var totalPriceStringProperty = SimpleStringProperty("Total price $totalPrice €")
    private val mainView: MainView by inject()

    fun refreshData() {
        val data = AppData.products.filter { it.isActive && it.stock > 0 }
        products.setAll(data)
        setFilteredData(filterInUse)
    }

    fun addProductToBasket(): Boolean {
        var item = hasItem(selectedProduct.name)
        if (item != null) { //item already in basket
            if (selectedProduct.stock < item.amount + 1) //stock limit
                return false

            item.amount++
            item.totalPrice = ((item.amount * item.pricePerOne) * 100).roundToInt() / 100.0
            productsInBasket[selectedProduct] = item.amount
        } else { // item not in basket
            item = InvoiceItem(selectedProduct.name, 1, selectedProduct.sellingPrice)
            productsInBasket[selectedProduct] = 1
            basket.add(item)
        }
        basket.sortBy { it.productName }
        log.info { "Product ${item.productName} successfully added to basket" }
        totalPrice = getTotalPriceOfBasket()
        totalPriceStringProperty.set("Total price $totalPrice €")
        setTotalAmountOfItemsTabName()
        return true
    }

    fun removeFromBasket() {
        val item = hasItem(selectedInvoiceItem.productName)
        if (item != null) {
            basket.remove(item)
            selectedInvoiceItem = InvoiceItem()
            productsInBasket[selectedProduct] = 0
            log.info { "Product ${item.productName} successfully removed from basket" }
            basket.sortBy { it.productName }
            totalPrice = getTotalPriceOfBasket()
            totalPriceStringProperty.set("Total price $totalPrice €")
            setTotalAmountOfItemsTabName()
        }
    }

    fun printInvoice() {
        val invoice = Invoice()
        invoice.seller = AppData.loggedInUser
        invoice.totalPrice = getTotalPriceOfBasket()
        basket.forEach {
            invoice.products.add(it)
        }
        log.info { "Invoice created by seller ${invoice.seller.name} ${invoice.seller.surname} for ${invoice.products.size} products." }
        InvoiceDatabase.insert(invoice)
        AppData.invoices.add(invoice)
        updateProductStockAndSavePurchaseHistory()
        invoice.saveToFile()
        productsInBasket = HashMap<Product, Int>()
        basket.setAll()
        refreshData()
        setTotalAmountOfItemsTabName()
        totalPrice=getTotalPriceOfBasket()
        totalPriceStringProperty.set("Total price $totalPrice €")
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

    fun setFilteredData(filter: String) {
        filteredProducts.setAll(products.filter { it.name.lowercase().contains(filter) || it.barcode.contains(filter) })
        filterInUse = filter
    }

    private fun getTotalPriceOfBasket(): Double {
        var totalPrice = 0.0
        basket.forEach {
            totalPrice += it.totalPrice
        }
        return (totalPrice * 100).roundToInt() / 100.0
    }

    fun setItemAmount(amount: Int): Boolean {
        if (amount == 0) {
            removeFromBasket()
            return true
        }

        val index = basket.indexOf(selectedInvoiceItem)
        val product = findProductByName(selectedInvoiceItem.productName)
        if (product == null || product.stock < amount) //stock limit
            return false


        basket[index].amount = amount
        basket[index].totalPrice = ((basket[index].amount * basket[index].pricePerOne) * 100).roundToInt() / 100.0
        productsInBasket[product] = basket[index].amount
        println(basket)
        productsInBasket.forEach {
            println("${it.key.name} -> ${it.value}")
        }
        log.info("Amount of ${product.name} has been set to $amount")
        setTotalAmountOfItemsTabName()
        totalPrice=getTotalPriceOfBasket()
        totalPriceStringProperty.set("Total price $totalPrice €")

        return true
    }

    fun findProductByName(productName: String): Product? {
        return products.find { it.name == productName }
    }

    fun setTotalAmountOfItemsTabName() {
        var total = 0
        basket.forEach {
            total += it.amount
        }
        mainView.setBasketAmount(total)
    }
}
