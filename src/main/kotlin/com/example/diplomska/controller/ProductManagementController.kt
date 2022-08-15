package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.Product
import com.example.diplomska.model.ProductStock
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class ProductManagementController : Controller() {
    var errorMessage: String = ""
    val products = AppData.products.asObservable()
    var filteredProducts: ObservableList<Product> = FXCollections.observableArrayList<Product>(products)
    var selectedProduct = Product()
    fun addProduct(product: Product): Boolean {
        val sameID = ProductDatabase.getById(product._id)
        val sameBarcode = ProductDatabase.getByBarcode(product.barcode)
        if (sameID != null || sameBarcode != null) {
            errorMessage = "Error when trying to add product. Barcode already exists"
            log.info { errorMessage }
            return false
        } else {
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

    fun deleteProduct(product: Product): Boolean {
        if (ProductDatabase.delete(product)) {
            products.remove(product)
            log.info { "Product ${product.name} successfully deleted" }
            return true
        } else {
            errorMessage = "Error when trying to delete product ${product.name}"
            log.info { errorMessage }
            return false
        }
    }

    fun updateProduct(product: Product): Boolean {
        val sameBarcode = ProductDatabase.getByBarcode(product.barcode)
        if (sameBarcode != null && sameBarcode._id != product._id) {
            errorMessage = "Error when trying to update product. Barcode already exists"
            log.info { errorMessage }
            return false
        } else {
            if (ProductDatabase.update(product)) {
                log.info { "Product ${product.name} successfully updated" }
                return true
            } else {
                errorMessage = "Error when updating product ${product.name}"
                log.info { errorMessage }
                return false
            }
        }
    }

    fun addStockToProduct(stock: ProductStock): Boolean {
        val lastPurchasePrice = selectedProduct.lastPurchasePrice
        selectedProduct.purchaseHistory.add(stock)
        selectedProduct.stock += stock.amount
        selectedProduct.lastPurchasePrice = stock.pricePerOne
        if (updateProduct(selectedProduct)) {
            log.info { "Stock to ${selectedProduct.name} successfully updated" }
            return true
        } else {
            selectedProduct.purchaseHistory.remove(stock)
            selectedProduct.stock -= stock.amount
            selectedProduct.lastPurchasePrice = lastPurchasePrice
            errorMessage = "Error when adding stock to product ${selectedProduct.name}"
            log.info { errorMessage }
            return false
        }
    }


    fun setFilteredData(filter: String) {
        filteredProducts.setAll(products.filter { it.name.lowercase().contains(filter) || it.barcode.lowercase().contains(filter) })
    }
}