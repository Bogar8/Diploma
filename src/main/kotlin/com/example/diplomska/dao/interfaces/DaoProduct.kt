package com.example.diplomska.dao.interfaces

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product

interface DaoProduct : DaoCrud<Product> {
    fun getByBarcode(barcode: String): Product?
    fun getInStock(): ArrayList<Product>
    fun getOutOfStock(): ArrayList<Product>
    fun getByCategory(category: Category): ArrayList<Product>
    fun getActiveProducts(): ArrayList<Product>
    fun getInActiveProducts(): ArrayList<Product>
}